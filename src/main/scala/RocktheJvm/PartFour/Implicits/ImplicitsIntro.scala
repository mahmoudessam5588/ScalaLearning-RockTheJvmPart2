package RocktheJvm.PartFour.Implicits

import scala.language.implicitConversions

object ImplicitsIntro extends App {
  val pair: (String, String) = "Mahmoud" -> "5588"

  case class Person(name:String):
    def greet = s"Hi my name is $name"

  implicit def fromStringToPerson(str:String) :Person = Person(str)
  println("Mahmoud".greet)

  def increment(x:Int)(implicit amount :Int): Int = x+amount
  implicit val defaultAmount: Int = 10
  println(increment(2))


  case class Persons(name:String){
    def greet = s"$name"
  }
  object ahmed{
  implicit def stringToPersons(value:String):Persons= Persons(value)
  }
  import ahmed.*
    println(ahmed.stringToPersons("ahmed").greet)

  def decrement(x:Int)(implicit amount :Int):Int  = x-amount
  object defaultAmounts{
    implicit val defaultAmounts: Int = 10}
  import defaultAmounts.*
  println(decrement(10)(defaultAmounts.defaultAmounts))



}
