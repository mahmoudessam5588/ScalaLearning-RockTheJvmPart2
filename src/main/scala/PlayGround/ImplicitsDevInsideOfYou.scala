package PlayGround

import scala.language.{implicitConversions, postfixOps}

object ImplicitsDevInsideOfYou extends App {
  final case class Apple(weightInGrams: Int, color: String)



  /*implicit def appleCanBeCalledAsOrange(apple: Apple): Orange = {
    Orange(apple.weightInGrams)
  }*/
  final case class Orange(weightInGrams: Int) extends AnyVal

  final implicit class AppleWrapper(private val apple:Apple)extends AnyVal {
    def toOrange:Orange = Orange(apple.weightInGrams)
  }

  def function(orange: Orange): Unit = {
    println("---------------------")
    println(orange)
    println("----------------------")
  }
  val apples = AppleWrapper(Apple(300,"Red"))
  function(
    //appleCanBeCalledAsOrange(
      apples toOrange
    //)
  )
  final case class First(value:Int)extends AnyVal
  final case class Second(value:Int)extends AnyVal

  def sums(a:Int)(implicit b:Int,c:Int):Int = a+b+c
  def sumz(a:Int)(implicit b:First,c:Second):Int = a + b.value + c.value
  implicit val hiddenIntOne:Int = 4
  implicit val first:First = First(3)
  implicit val second:Second = Second(5)
  implicit val applez:Apple = Apple(300,"Red")
  implicit val Orangez:Orange = Orange(300)


  println(sums(1))
  println(sums(1)(3,5))
  println(sumz(1)(First(3),Second(5)))
  println(sumz(1))
  def getT[T](implicit t:T):T = t

  println(getT[First])
  println(implicitly[Orange])
  println(implicitly[Apple])
  //"---------------------------------------------"//
  trait TypeClass[T]{
    def someMethod(t:T):Int
  }
  implicit object instanceOfStrings extends TypeClass[String]{
    override def someMethod(t: String): Int = t.toInt
  }
  def algorithmOne[T](t:T)(implicit instance:TypeClass[T]):Unit= {
    println(instance.someMethod(t))
  }
  def algorithmsTwo[T:TypeClass](t:T):Unit={
    val instance = implicitly[TypeClass[T]]
    println(instance.someMethod(t))
  }
  def algorithmThree[T](t:T)(implicit convert: T => Int):Unit={
    println(convert(t))
  }

  def algorithm4ForAll[T <: Int](t:T):Unit={
    val convert = implicitly[T => Int]
    println(convert(t))
  }
  def conversion(x:String):Int = x.toInt
  algorithmOne("123")
  algorithmsTwo("123")
  algorithmThree(conversion("123"))
  algorithmThree(123)
  algorithm4ForAll(conversion("123"))
  algorithm4ForAll(123)


}
