package exercises

import lectures.part4implicits.TypeClasses.User

object EqualityPlayground extends App {
  /**
   * Equality
   */
  trait Equal[T] {
    def apply(a: T, b: T): Boolean
  }


  implicit object NameEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name
  }

  object FullEquality extends Equal[User] {
    override def apply(a: User, b: User): Boolean = a.name == b.name && a.email == b.email
  }

  /*
   Exercise: implement the TypeClass pattern for the equality type class
  */
  object Equal {
    def apply[T](a:T, b: T)(implicit equalizer: Equal[T]): Boolean =
      equalizer(a, b)
  }

  val john = User("John", 32, "zhe@gmail.com")

  val anotherJohn = User("John", 45, "anotherJohn@rtjvm.com")
  println(Equal(john, anotherJohn))
  // AD-HOC polymorphism

  /*
    Exercise - improve the Equal TypeClass with an implicit conversion class
      ===(anotherValue: T)
      !==(anotherValue: T)
   */

  implicit class TypeSafeEqual[T](value: T) {

    def ===(anotherValue: T)(implicit equalizer: Equal[T]): Boolean = equalizer(value, anotherValue)

    def !==(implicit equalizer: Equal[T], anotherValue: T): Boolean = !equalizer(value, anotherValue)
  }

  println(john === anotherJohn)
  /*
    john.===(anotherJohn)
    new TypeSafeEqual[User](john).===(anotherJohn)
    new TypeSafeEqual[User](john).===(anotherJohn)(NameEquality)
   */

  /*
    TYPE SAFE
   */

  println(john == 43)
//  println(john === 43) // TYPE SAFE


}
