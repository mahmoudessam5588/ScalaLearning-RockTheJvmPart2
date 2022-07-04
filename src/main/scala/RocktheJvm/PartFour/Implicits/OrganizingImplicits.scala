package RocktheJvm.PartFour.Implicits

object OrganizingImplicits extends App {
  implicit def reverseOrdering():Ordering[Int] = Ordering.fromLessThan(_<_)
  List(33,56,79,30).sorted.foreach(println)

  case class Person(name:String,age:Int)
  object alphabetizer {
    implicit def alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan((a, b) => a.name.compareTo(b.name) < 0)
  }
  object ageOrdering{
    implicit def ageOrdering:Ordering[Person] =Ordering.fromLessThan((a,b)=> a.age < b.age)
  }
    val person = List(
    Person("Zakaria",33),
    Person("Omar",36),
    Person("Essam",45),
  )
  //import ageOrdering.*
  import alphabetizer.*
  println(person.sorted)
  /*
  * Impilicit Scope
  * normal scope  = Local scope
  * Imported scope
  * companion in all types match the method signature*/
}
