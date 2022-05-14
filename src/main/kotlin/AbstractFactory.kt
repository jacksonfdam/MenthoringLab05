/*
Abstract Factory

Abstract Factory is a creational design pattern that lets you produce families of related objects without specifying
their concrete classes.

Problem:

Imagine you’re creating a furniture shop simulator. Your code consists of classes that contain that represent

Let’s consider products Chair, Sofa and Coffee table

And variants of these products let’s say Modern, Victorian, and ArtDeco

You need a way to create individual furniture objects so that they match other objects of the same family.
Customers get quite mad when they receive non-matching furniture.

Also, you don’t want to change existing code when adding new products or families of products to the program.
Furniture vendors update their catalogues very often, and you wouldn’t want to change the core code each time it happens.

Solution:

The first thing the Abstract Factory pattern suggests is to explicitly declare interfaces for each distinct product of
the product family (e.g., chair, sofa or coffee table). Then you can make all variants of products follow those
interfaces. For example, all chair variants can implement the Chair interface; all coffee table variants can implement
the CoffeeTable interface, and so on.


              ┌──────────────────┐
              │     Delivery     │
              ├──────────────────┼─────┐
        ┌─────┤ getTransportType │     │
        │     │ planDelivery     │     │
        │     │                  │     │
        │     └──────────────────┘     │
        │                              │
┌───────▼────────┐          ┌──────────▼──────┐
│     Road       │          │     Air         │
├────────────────┤          ├─────────────────┤
│                │          │                 │
│  planDelivery()│          │  planDelivery() │
│                │          │                 │
└────────────────┘          └─────────────────┘


The next move is to declare the Abstract Factory — an interface with a list of creation methods for all products that
are part of the product family (for example, createChair, createSofa and createCoffeeTable).

These methods must return abstract product types represented by the interfaces we extracted previously:
Chair, Sofa, CoffeeTable and so on.

                    ┌──────────────────────────────────────┐
                    │      FurnitureFactory                │
                    ├──────────────────────────────────────┤
                    │  + createChair(): Chair              │
                    │  + createCoffeeTable(): CoffeeTable  │
                    │  + createSofa(): Sofa                │
                    └────────────────▲─────────────────────┘
                                     │
                                     │
             ┌───────────────────────┴─────────────────────────┐
             │                                                 │
             │                                                 │
┌────────────▼───────────────────────┐      ┌──────────────────▼─────────────────┐
│ VictorianFurnitureFactory          │      │ ModernFurnitureFactory             │
├───────────────────────────────────┬┤      ├────────────────────────────────────┤
│ + createChair(): Chair            └┤      │ + createChair(): Chair             │
│ + createCoffeeTable(): CoffeeTable │      │ + createCoffeeTable(): CoffeeTable │
│ + createSofa(): Sofa               │      │ + createSofa(): Sofa               │
└────────────────────────────────────┘      └────────────────────────────────────┘

                Abstract factory Method Pattern 1.1

 */

interface Chair {
    fun hasLegs(): Boolean
    fun sitOn(): String
}

interface CofeeTable {
    fun hasLegs(): Boolean
    fun changeColor(): String
}

interface Sofa {
    fun size(): Int
}

class VictorianChair : Chair {
    override fun hasLegs(): Boolean {
        return true
    }

    override fun sitOn(): String {
        return "Inside sitOn() function"
    }
}

class ModernChair : Chair {
    override fun hasLegs(): Boolean {
        return true
    }

    override fun sitOn(): String {
        return "Inside sitOn() function"
    }
}

interface FurnitureFactory {
    fun createChair(): Chair
    fun createCoffeeTable(): CofeeTable
    fun createSofa(): Sofa
}

class VictorianFurnitureFactory : FurnitureFactory {
    override fun createChair(): Chair {
        return VictorianChair()
    }

    override fun createCoffeeTable(): CofeeTable { TODO("Not yet implemented") }

    override fun createSofa(): Sofa { TODO("Not yet implemented") }
}

class ModernFurnitureFactory : FurnitureFactory {
    override fun createChair(): Chair {
        return ModernChair()
    }

    override fun createCoffeeTable(): CofeeTable { TODO("Not yet implemented") }

    override fun createSofa(): Sofa { TODO("Not yet implemented") }
}

/*
The client code has to work with both factories and products via their respective abstract interfaces.
This lets you change the type of a factory that you pass to the client code, as well as the product variant that the
client code receives, without breaking the actual client code.

Pros:
 - You avoid tight coupling between concrete products and client code.
 - You can be sure that the products you’re getting from a factory are compatible with each other

Cons:
 - The code may become more complicated than it should be since a lot of new interfaces and classes are introduced
 along with the pattern.
 */
