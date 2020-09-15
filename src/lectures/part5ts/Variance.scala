package lectures.part5ts

object Variance extends App {

  trait Animal
  class Dog extends Animal
  class Cat extends Animal
  class Crocodile extends Animal

  // what is variance?
  // "inheritance" - type substitution of generics

  // Should a cage cat inherit from cage animal?
  class Cage[T]
  // yes - covariance
  class CCage[+T]
  val ccage: CCage[Animal] = new CCage[Cat]

  // no - invariance
  class ICage[T]
//  val icage: CCage[Animal] = new ICage[Cat]
  // like we are doing     val x: Int = "hello"

  // hell no - opposite = contravariance
  class XCage[-T]
  val xcage: XCage[Cat] = new XCage[Animal]

  class InvariantCage[T](val animal: T) // invariant

  // covariant positions
  class CovariantCage[+T](val animal: T) // COVARIANT POSITION

//  class ContravariantCage[-T](val animal: T) //
  /*
    If the above is good, then we can write something like:

    val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)
   */

//  class CovariantVariableCage[+T](var animal: T) // types of vars are in CONTRAVARIANT POSITION
  /*
    If the above is good, then we can write something like:

    val ccage: CCage[Animal] = new CCage[Cat](new Cat) -> fine
    ccage.animal = new Crocodile ( because the instantiation is more specific)
   */

//  class ContravariantVariableCage[-T](var animal: T) // also in COVARIANTE POSITION
  /*
    Totally wrong below:

    val catCage: XCage[Cat] = new XCage[Animal](new Crocodile)
   */

  // the only accpetable field for a field is invariant position
  class InvariantVariableCage[T](var animal: T) // ok

  trait AnotherCovariantCage[+T] {
//    def addAnimal(animal: T) // CONTRAVARIANT POSITION
  /*
    below is very wrong:

    val ccage: CCage[Animal] = new CCage[Dog]
    ccage.add(new Cat)
   */

    class AnotherContravariantCage[-T] {
      def addAnimal(animal: T) = true
    }
    val acc: AnotherContravariantCage[Cat] = new AnotherContravariantCage[Animal]
//    acc.addAnimal(new Dog)
    acc.addAnimal(new Cat)

    class Kitty extends Cat
    acc.addAnimal(new Kitty)

    class MyList[+A] {
      def add[B >: A](element: B): MyList[B] = new MyList[B] // widening the type
    }

    val emptylist = new MyList[Kitty]
    val animals = emptylist.add(new Kitty)
    val moreAnimals = animals.add(new Cat)
    val evenMoreAnimals = moreAnimals.add(new Dog)

    // METHOD ARGUMENTS ARE IN CONTRAVARIANT POSITION.

    // return types
    class PetShop[-T] {
//      def get(isItaPuppy: Boolean): T // METHOD RETURN TYPES ARE IN COVARIANT POSITION
      /*
        val catShop = new PerShop[Animal]
          def get(isItaPuppy: Boolean): Animal = new Cat

       val dogShop: PetShop[Dog] = catShop
       dogShop.get(true) // EVIL CAT!
       */

      def get[S <: T](isItaPuppy: Boolean, defaultAnimal: S): S = defaultAnimal
    }

    val shop: PetShop[Dog] = new PetShop[Animal]
//    val evilCat = shop.get(true, new Cat)
    class TerraNova extends Dog
    val bigFurry = shop.get(true, new TerraNova)

    /*
      Big rule, for scala functions
      - method arguments are in CONTRAVARIANT position
      - return types are in COVARIANT position
     */



  }

  /**
   * 1.
   * Invariant, covariant, contravariant
   *  Parking[T](things: List[T]) {
   *    park(vehicle: T)
   *    impound(vehicles: List[T])
   *    checkVehicles(conditions: String): List[T]
   *  }
   *
   *  2. used someone else's API: InvariantList[T] (IList)
   *  3. Parking = monad!
   *    - flatMap
   */
  class Vehicle
  class Bike extends Vehicle
  class Car extends Vehicle
  class IList[T]

  class IParking[T](things: List[T]) {
    def park(vehicle: T) = ???
    def impound(vehicles: List[T]) = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  class CParking[+T](things: List[T]) {
    def park[B >: T](vehicle: B) = ???
    def impound[B >: T](vehicles: List[B]) = ???
    def checkVehicles(conditions: String): List[T] = ???

    def flatMap[S](f: T => IParking[S]): IParking[S] = ???
  }

  class XParking[-T](things: List[T]) {
    def park(vehicle: T) = ???
    def impound(vehicles: List[T]) = ???
    def checkVehicles[B <: T](conditions: String): List[B] = ???

    def flatMap[R <: T, S](f: R => IParking[S]): IParking[S] = ???
  }

  /*
    Rule of thumb
    - use covariance = COLLECTION OF THINGS
    - use contravariance = GROUP OF ACTIONS
   */

  class CParking2[+T](things: IList[T]) {
    def park[B >: T](vehicle: B) = ???
    def impound[B >: T](vehicles: IList[B]) = ???
    def checkVehicles[B >: T](conditions: String): IList[B] = ???
  }

  class XParking2[-T](things: IList[T]) {
    def park(vehicle: T) = ???
    def impound[B <: T](vehicles: IList[B]) = ???
    def checkVehicles[B <: T](conditions: String): IList[B] = ???
  }

  // flatMap

}
