package RocktheJvm.PartFour.Implicits

import RocktheJvm.PartFour.Implicits.TypeClassesOfScala.HTMLImplicitSerializer
import RocktheJvm.PartFour.Implicits.TypeClassesOfScala.HTMLImplicitSerializer.*

object TypeClassesOfScala extends App {
  trait HTMLRendering {
    def toHtml: String
  }


  case class miniSocialMedia(name: String, age: Int, email: String) extends HTMLRendering {
    override def toHtml: String = s"<div> $name & $age <a href=$email/> </div>"
  }


  trait HTMLImplicitSerializer[T] {
    def serialize(value: T): String
  }


  object HTMLImplicitSerializer {
    def serialize[T](value: T)(implicit serializer: HTMLImplicitSerializer[T]): String =
      serializer.serialize(value)

    def apply[T](implicit serializer: HTMLImplicitSerializer[T]): HTMLImplicitSerializer[T] = serializer
  }


  implicit object userSerializer extends HTMLImplicitSerializer[miniSocialMedia] {
    override def serialize(value: miniSocialMedia): String = {
      s"<div> ${value.name} & ${value.age} <a href=${value.email}/> </div>"
    }
  }

  val ahmed = miniSocialMedia("Ahmed", 35, "Ahmed@gmail.com")
  println(userSerializer.serialize(ahmed))
  //1- we can define serializer for other types

  import java.util.Date

  object DateSerializer extends HTMLImplicitSerializer[Date] {
    override def serialize(date: Date): String = s"${date.toString}"
  }

  //2- we can define multiple serializer for certain types
  object PartialUserSerialize extends HTMLImplicitSerializer[miniSocialMedia] {
    override def serialize(value: miniSocialMedia): String = {
      s"<div> ${value.name} & ${value.age} </div>"
    }
  }

  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }

  object nameEquality extends Equal[miniSocialMedia] {
    override def apply(a: miniSocialMedia, b: miniSocialMedia): Boolean = a.name == b.name
  }

  object fullEquality extends Equal[miniSocialMedia] {
    override def apply(a: miniSocialMedia, b: miniSocialMedia): Boolean = a.name == b.name && a.email == b.email
  }
  // part 2

  implicit object InstSerializer extends HTMLImplicitSerializer[Int] {
    override def serialize(value: Int): String = s" Implicit Value : $value "
  }

  /*lost type safety , need to modify code each time ,still one implementation*/
  /*
  * disadvantages:
   - only for types we write
   * un reusable for multiple features*/
  //option 2 pattern matching
  /*object HTMLSerializer {
    def serialization(value: Any): Unit = value match
      case _ =>
  }*/








  println(HTMLImplicitSerializer.serialize(42)(InstSerializer))
  println(HTMLImplicitSerializer.serialize(42))
  println(HTMLImplicitSerializer.serialize(ahmed))
  println(HTMLImplicitSerializer[miniSocialMedia].serialize(ahmed))


  trait MyTypeClassTemplate[T] {
    def action(value: T): String
  }

  object MyTypeClassTemplate {
    def apply[T](implicit instance: MyTypeClassTemplate[T]): MyTypeClassTemplate[T] = instance
  }


}
