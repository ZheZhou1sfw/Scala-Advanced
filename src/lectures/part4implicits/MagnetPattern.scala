package lectures.part4implicits

import exercises.MySetPlayground.s

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

object MagnetPattern extends App {

  // method overloading

  class P2PRequest

  class P2PResponse

  class Serializer[T]

  trait Actor {
    def receive(statusCode: Int): Int

    def receive(request: P2PRequest): Int

    def receive(response: P2PResponse): Int

    def receive[T: Serializer](message: T)

    def receive[T: Serializer](message: T, statusCode: Int): Int

    def receive(future: Future[P2PRequest])

    // lots of overloads ^^^
  }

  /*
    Problems:
    1 - type erasure (T is erasured at compile time)
    2 - lifting doesn't work for all overloads

      val receiveFV = receive _ //

    3 - code duplication
    4 - type inference and default args
   */

  trait MessageManet[Result] {
    def apply(): Result
  }

  def receive[R](magnet: MessageManet[R]): R = magnet()

  implicit class FromP2PRequest(request: P2PRequest) extends MessageManet[Int] {
    def apply(): Int = {
      // logic for handling a P2PRequest
      println("Handling P2P request")
      42
    }
  }

  implicit class FromP2PResponse(response: P2PResponse) extends MessageManet[Int] {
    def apply(): Int = {
      // logic for handling a P2PRequest
      println("Handling P2P response")
      24
    }
  }

  receive(new P2PRequest)
  receive(new P2PResponse)

  // 1 - no more type erasure problems
  implicit class FromResponseFuture(future: Future[P2PResponse]) extends MessageManet[Int] {
    override def apply(): Int = 2
  }

  implicit class FromRequestFuture(future: Future[P2PRequest]) extends MessageManet[Int] {
    override def apply(): Int = 4
  }
  println(receive(Future(new P2PRequest)))
  println(receive(Future(new P2PRequest)))

  // 2 - lifting works
  trait MathLib {
    def add1(x: Int) = x + 1
    def add1(s: String) = s.toInt + 1
    // add1 overloads
  }

  // "magnetize"
  trait AddMagnet {
    def apply(): Int
  }

  def add1(magnet: AddMagnet): Int = magnet()

  implicit class AddInt(x: Int) extends AddMagnet {
    override def apply(): Int = x + 1
  }

  implicit class AddString(s: String) extends AddMagnet {
    override def apply(): Int = s.toInt + 1
  }

  val addFV = add1 _
  println(addFV(1))
  println(addFV("3"))

  /*
    Draw backs
    1 - verbose
    2 - hard to read
    3 - you can't name or place default arguments
    4 - call by name doesn't work correctly
   */

  // prove '4'
  class Handler {
    def handle(s: => String) = {
      println(s)
      println(s)
    }
    // other ovealoads
  }

  trait HandleMagnet {
    def apply(): Unit
  }

  def handle(magnet: HandleMagnet) = magnet()

  implicit class StringHandle(s: => String) extends HandleMagnet {
    override def apply(): Unit = {
      println(s)
      println(s)
    }

    def sideEffectMethods(): String = {
      println("Hello, Scala")
      "magnet"
    }

    handle(sideEffectMethods())
    handle {
      println("Hello, Scala")
      "magnet"
    }
  }

}
