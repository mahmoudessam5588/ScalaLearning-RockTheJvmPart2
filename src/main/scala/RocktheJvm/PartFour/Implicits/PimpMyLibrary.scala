package RocktheJvm.PartFour.Implicits

import java.security.Principal
import scala.annotation.{tailrec, targetName}
import scala.language.{implicitConversions, postfixOps}
import scala.util.Random

object PimpMyLibrary extends App {

  implicit class RichInt(val value: Int) extends AnyVal {
    def isPrime: Boolean = value % 2 == 0

    def sqrt: Double = Math.sqrt(value)

    def times(function: () => Unit): Unit = {
      def timesAux(n: Int): Unit = {
        if n <= 0 then ()
        else
          function()
          timesAux(n - 1)

        timesAux(value)
      }
    }

    @targetName("List Concatenation")
    def *[T](list: List[T]): List[T] = {
      def concat(n: Int): List[T] = {
        if n <= 0 then {
          list
        }
        else concat(n - 1) ++ list
      }

      concat(value)
    }


  }

  println(RichInt(42).isPrime)
  println(2 sqrt)
  println(42 isPrime)

  import scala.concurrent.duration.*

  2 seconds
  //compiler doesn't do multiple implicit searches
  val random: Random = new Random()

  implicit class RichString(val value: String) extends AnyVal {

    def toInts: Int = value.toInt

    def encrypts: String = value.toList.reverse.mkString("").toLowerCase

    def cypherEncryption(cypherDistance: Int): String = value.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("1".toInts + 4)
  println("Mahmoud" encrypts)
  println("Ahmed" cypherEncryption 10)
  println("-------- ")
  println(RichInt(3)*List(1,2,3))
  3 times(()=>println("Bye"))



}
