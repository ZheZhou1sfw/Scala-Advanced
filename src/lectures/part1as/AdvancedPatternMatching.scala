package lectures.part1as

object AdvancedPatternMatching extends App {

  val numbers = List(1)
  val description = numbers match {
    case head :: Nil => println(s"the only element is $head.")
    case _ =>
  }

  /*
    - constants
    - wildcards
    - case classes
    - tuples
    - some special magic like above
   */

  class Person(val name: String, val age: Int)

  object Person {
    def unapply(person: Person): Option[(String, Int)] = Some((person.name, person.age))
  }

  val bob = new Person("Bob", 25)
  val greeting = bob match {
      // unapply does the trick
    case Person(n, a) => s"Hi my name is $n and I am $a years old."
  }

  println(greeting)
}
