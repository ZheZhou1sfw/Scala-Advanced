package lectures.part4implicits

object OrganizingImplicits extends App {

  implicit val reverseOrdering: Ordering[Int] = Ordering.fromLessThan(_ > _)
//  implicit val normalOrdering: Ordering[Int] = Ordering.fromLessThan(_ <= _)
  println(List(1,4,5,3,2).sorted)

  // scala.Predef

  /*
    Implicits (used as implicit parameters):
      - val/var
      - object
      - accessor methods = defs with no parentheses
   */

  // Exercise
  case class Person(name: String, age: Int)

  val persons = List(
    Person("Steve", 30),
    Person("Amy", 22),
    Person("John", 66)
  )

//  implicit val personOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)

//  println(persons.sorted)

  /*
    Implicit scope
    - normal scope = LOCAL SCOPE
    - imported scope
    - companions (object) of all types involved in the method signature


    Best practices:
      1. define the implicit in the companion
      2. if there are many possible values, define the good implicit in the companion
   */

  object AlphabeticNameOrdering {
    implicit val alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan(_.name < _.name)
  }

  object AgeOrdering {
    implicit val alphabeticOrdering: Ordering[Person] = Ordering.fromLessThan(_.age < _.age)
  }

  import AlphabeticNameOrdering._
  println(persons.sorted)

  /*
    Exercise
   */
  case class Purchase(nUnits: Int, unitPrice: Double)

  object totalPriceOrdering {
    implicit val totalPriceOrdering: Ordering[Purchase] = Ordering.fromLessThan((a, b) => a.unitPrice * a.nUnits < b.unitPrice * b.nUnits)

  }

}
