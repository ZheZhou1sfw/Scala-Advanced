package lectures.part4implicits

import java.{util => ju}

object ScalaJavaConversions extends App {

  import scala.jdk.CollectionConverters._

  val javaSet: ju.Set[Int] = new ju.HashSet[Int]()
  (1 to 5).foreach(javaSet.add)
  println(javaSet)

  val scalaSet = javaSet.asScala

  println(scalaSet) // using implicit conversion

  /*
    Iterator
    Iterable
    ju.List -> scala.mutable.Buffer
    ju.Set - scala.mutable.Set
    ju.Map - scala.mutable.Map
   */

  import collection.mutable._
  val numbersBuffer = ArrayBuffer[Int](1,2,3) // mutable
  val juNumbersBuffer = numbersBuffer.asJava
  println(juNumbersBuffer)
  println(juNumbersBuffer.asScala eq numbersBuffer) // conversion back has the same reference (only for mutable types)

  val numbers = List(1,2,3) // immutable List
  val juNumbers = numbers.asJava // stay immutable in Java
  val backToScala = juNumbers.asScala // backToScala is a mutable Buffer
  println(backToScala eq numbers) // false
  println(backToScala == numbers) // false

//  juNumbers.add(4) // throw exception because the original scala list is immutable

  /*
    Exercise
    create a Scala-Java Optional-Option
   */

  class ToScala[T](value: => T) {
    def asScala: T = value
  }

  implicit def asScalaOptional[T](o: ju.Optional[T]): ToScala[Option[T]] = new ToScala[Option[T]](
    if (o.isPresent) Some(o.get) else None
  )

  val juOptional: ju.Optional[Int] = ju.Optional.of(2)
  val scalaOption = juOptional.asScala
  println(scalaOption)

}
