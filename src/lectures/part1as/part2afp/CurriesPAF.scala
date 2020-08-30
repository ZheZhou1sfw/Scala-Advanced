package lectures.part1as.part2afp

object CurriesPAF extends App {

  // curried functions
  val superAdder: Int => Int => Int =
    x => y => x + y

  val add3 = superAdder(3)
  println(superAdder(3)(5)) // curried function

  // METHOD!
  def curriedAdder(x: Int)(y: Int): Int = x + y

  val add4: Int => Int = curriedAdder(4)

  // lifting = ETA-EXPANSION

  // functions != methods
  def inc(x: Int) = x + 1
  List(1,2,3).map(inc) // ETA-expansion

  // Partial function applications
  val add5 = curriedAdder(5) _  // "_" -> ETA-expansion (converted to Int => Int)

  // EXERCISE
  val simpleAddFunction = (x: Int, y: Int) => x + y
  def simpleAddMethod(x: Int, y: Int) = x + y
  def curriedAddMethod(x:Int)(y: Int) = x + y

  // add7: Int => Int = y => 7 + y
  val first7 = (x: Int) => simpleAddFunction(7, x)
  val second7 = (x: Int) => simpleAddFunction.curried(7)

  val add7_3 = curriedAddMethod(7) _
  val add7_4 = curriedAddMethod(7)(_) // PAF alternative syntax

  val add7_5 = simpleAddMethod(7, _: Int) // alternative syntax for turning methods into function values
                // y => simpleAddMethod(7,y)

  // underscores are powerful
  def concatenator(a: String, b: String, c: String) = a + b + c
  val insertName = concatenator("Hello, I'm ", _:String, ", how are you")
  println(insertName("Daniel"))

  val fillInTheBlanks = concatenator("Hello, ", _:String, _:String) // (x, y) => concatenator("Hello", x, y)
  println(fillInTheBlanks("Daniel", "Scala is awesome!"))

  // EXERCISES
  /*
    1. Process a list of numbers and return their string representations with different formats
   */

  
}
