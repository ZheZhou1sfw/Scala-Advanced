package lectures.part4implicits

object PimpMyLibrary extends App {

  // 2.isPrime

  implicit class RichInt(val value: Int) extends AnyVal {
    def isEven: Boolean = value % 2 == 0
    def sqrt: Double = Math.sqrt(value)

    def times(function: () => Unit): Unit = {
      def timesAux(n: Int): Unit = {
        if (n <= 0) ()
        else {
          function()
          timesAux(n - 1)
        }
        timesAux(value)
      }
    }

    def *[T](list: List[T]): List[T] = {
      def concatenate(n: Int): List[T] =
        if (n <= 0) List()
        else concatenate(n - 1) ++ list

      concatenate(value)
    }
  }

  implicit class RicherInt(richInt: RichInt) {
    def isOdd: Boolean = richInt.value % 2 != 0
  }
  new RichInt(42).sqrt
  42.isEven // new RichInt(42).isEven

  // type erichment = pimping

  1 to 10

  import scala.concurrent.duration._
  3.seconds

  // compiler doesn't do multiple implicit searches
  //  42.isOdd

  /*
    Exercises
   */
  implicit class RichString(val str: String) extends AnyVal {
    def asInt: Int = Integer.valueOf(str)
    def encrypt(cypherDistance: Int): String =
      str.map(c => (c + cypherDistance).asInstanceOf[Char])
  }

  println("234".asInt + 4)
  println("John".encrypt(2))

  3.times(() => println("Scala Rocks!"))
  println(4 * List(1,2))

  // danger zone : hard to debug
  // avoid implicit defs as much as possible
  implicit def intToBoolean(i: Int): Boolean = i == 1

  /*
    if (n) do something
    else do something else
   */

  val aConditionedValue = if(3) "OK" else "Wrong thing"
  println(aConditionedValue)
}
