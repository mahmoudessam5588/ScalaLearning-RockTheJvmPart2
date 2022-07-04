package FuturePromisesRevision
import scala.concurrent.*
import concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object FuturePromisesRevisions extends App{
  def computing: Int = {
    Thread.sleep(1000)
    42
  }
  val future = Future {
   computing
  }

  future.onComplete(
    _  match
      case Success(value) => println(s"The Computed Value is $value")
      case Failure(exception) => println(s"Failed Due To: ${exception.printStackTrace()}")
  )
  Thread.sleep(2000)


}

