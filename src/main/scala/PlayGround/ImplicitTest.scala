package PlayGround

import scala.annotation.targetName
import scala.language.postfixOps

object ImplicitTest extends App {
  trait HtmlSerializer[T] {
    def serialize(value: T): String
  }

  object HtmlSerializer {
    def serialize[T](value: T)(implicit serializer: HtmlSerializer[T]): String = serializer.serialize(value)

    def apply[T](implicit serializer: HtmlSerializer[T]): HtmlSerializer[T] = serializer
  }

  implicit class HtmlEnhancer[T](value: T) {
    def toHtml(implicit serializer: HtmlSerializer[T]): String = serializer.serialize(value)

  }

  case class User(name: String, email: String, age: Int) {
    def toHtML: String = s"$name && $email && $age "
  }

  implicit object StringSerializer extends HtmlSerializer[User] {
    override def serialize(value: User): String = s"Implicit String[User] : ${value.name} , ${value.email}"
  }

  implicit object StringSerializers extends HtmlSerializer[String] {
    override def serialize(value: String): String = s"Implicit String : $value"
  }

  implicit object IntSerializer extends HtmlSerializer[User] {
    override def serialize(value: User): String = s"Implicit Int[User]: ${value.age}"
  }

  implicit object IntSerializers extends HtmlSerializer[Int] {
    override def serialize(value: Int): String = s"Implicit Int :$value"

  }


  val userOne = User("Ahmed", "Ahmed@gamil.com", 22)

  println(StringSerializer.serialize(userOne))

  println(IntSerializer.serialize(userOne))

  println(HtmlSerializer[User](StringSerializer).serialize(userOne))

  println(HtmlSerializer[User](IntSerializer).serialize(userOne))
  val users = HtmlSerializer[String](StringSerializers)

  println(HtmlSerializer.serialize(userOne)(StringSerializer))

  println(HtmlSerializer.serialize(42))

  println(HtmlSerializer.serialize("Ahmed"))

  println("-------------")

  println("Ahmed" toHtml users)
  println("Ahmed" toHtml)
  println(2 toHtml)
  /*
  * type class itself HtmlSerializer[T]{....}
  * type class instances UserSerializer , StringSerializer ,ImtSerializer
  * conversion with implicit classes RichInt , HtmlEnhancer*/

  println("-------------")

  //"--------------------------------------------------"
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  trait MyTypeClass[T] {
    def action(value: T): String
  }

  object MyTypeClass {
    def apply[T](implicit instance: MyTypeClass[T]): MyTypeClass[T] = instance
  }

  object Equal {
    def apply[T](a: T, b: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(a, b)
  }

  object nameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  implicit object fullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  /*implicit class HtmlEnhancer[T](value:T){
   def toHtml(implicit serializer:HtmlSerializer[T]): String = serializer.serialize(value)

  }*/
  implicit class Equals[T](value: T) {
    @targetName("Equality")
    def ===(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value, anotherValue)

    @targetName("non Equality")
    def !==(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = equalizer.apply(value, anotherValue)
  }

  val userTwo = User("Ahmed", "Ahmedz@gamil.com", 22)
  println(Equal.apply(userOne, userTwo))
  println(Equal(userOne, userTwo)) //AD-Hoc polymorphism
  println(userOne === userTwo)
  println(userOne !== userTwo)


}
