package RocktheJvm.PartFour

import java.util.concurrent.ForkJoinPool
import java.util.concurrent.atomic.AtomicReference
import scala.collection.parallel.immutable.*
import scala.collection.parallel.CollectionConverters.*
import scala.collection.parallel.ForkJoinTaskSupport

object ParallelUtil extends App {
  val Strings = List("a", "b").par
  val aParVector = ParVector[Int](1, 2, 3)

  /*
  * Seq
  * Vector
  * Array
  * Map  - Hash -tree
  * Set - Hash -tree*/
  def measure[T](operation: => T): Long = {
    val time = System.currentTimeMillis()
    operation
    System.currentTimeMillis() - time
  }
  val list =  (1 to 70000).toList
  val serialTime = measure{
    LazyList(1).map(_+1).take(70000).toList
  }
  val serialTime1 = measure{
    list.map(_+1)
  }
  val serialTime2 = measure{
    list.par.map(_+1)
  }
  println(s"serial time lazy "+ serialTime)
  println(s"serial time list "+ serialTime1)
  println(s"serial time par "+ serialTime2)
  //parallel collection with reduce and fold not recommended not associative operations
  println(List(1,2,3).reduce(_-_))
  println(List(1,2,3).par.reduce(_-_))
  //parallel collections lacks synchronization
  //var sum = 0
  //println(List(1,2,3).par.foreach(sum += _))
  //configuration
  aParVector.tasksupport  = new ForkJoinTaskSupport(new ForkJoinPool(2))
  /*
  * Alternatives
  * ThreadPoolTaskSupport -deprecated
  *ExecutionContextTaskSupport(EC) */
  //Atomic Operation
  val atomic  = new AtomicReference[Int](2)
  val threadSafe = atomic.get()
  atomic.set(4)
  atomic.getAndSet(5)
  atomic.compareAndSet(35,56) //shallow equality reference equality if present update the expected to new value
  atomic.updateAndGet(_+1)//thread safe function set
  atomic.getAndUpdate(_+1)
  atomic.accumulateAndGet(12,_+_)
  atomic.accumulateAndGet(12,_+_)

}
