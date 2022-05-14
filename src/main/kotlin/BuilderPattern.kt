/*
Design Patterns / Creational Patterns

Builder Pattern

Builder is a creational design pattern that lets you construct complex objects step by step.
The pattern allows you to produce different types and representations of an object using the same construction code.

Problem:

Imagine an object need step by step initialization of many fields and nested objects. Such initialization of code
usually makes our constructor with lots of parameters.

Let’s take an example,

To build a simple house, you need to construct four walls and a floor, install a door, fit a pair of windows, and
build a roof. But what if you want a bigger, brighter house, with a backyard and other goodies (like a heating system,
plumbing, and electrical wiring)

         ┌────────────────────────────────┐
         │ House                          │
         ├────────────────────────────────┤
    ┌────┤ +House(window, room, door,     ├────────────────┐
    │    │ hasGarden, hasSwimmingPool...) │                │
    │    │                                │                │
    │    └────────────────────────────────┘                │
    │                                                      │
    │                                                      │
    ▼                                                      ▼
new House(1,2,2, false, true...)         new House(6,4,4, true, true...)


                            Builder Pattern 1.0


In the above example, there is Class House with n number of parameters, most of the parameters will be unused(Not
necessary to have a swimming pool in every house) and making our constructor not readable.

Solution:

The Builder pattern suggests that you extract the object construction code out of its own class and move it to separate
objects called builders.

┌─────────────────────────┐
│ HouseBuilder            │
├─────────────────────────┤
│ + buildWindow()         │
│ + buildDoor()           │
│ + buildRoom()           │
│ + buildHasGarden()      │
│ + buildHasSwimmingPool()│
│ + getResult():House     │
└─────────────────────────┘

Builder Pattern 1.1

 */

import kotlin.properties.Delegates

data class House(
    var window: Int,
    var door: Int,
    var room: Int,
    var hasGarden: Boolean,
    var hasSwimmingPool: Boolean
) {

    private constructor(builder: HouseBuilder) : this(
        builder.window,
        builder.door,
        builder.room,
        builder.hasGarden,
        builder.hasSwimmingPool
    )

    class HouseBuilder {
        var window by Delegates.notNull<Int>()
        var door by Delegates.notNull<Int>()
        var room by Delegates.notNull<Int>()
        var hasGarden by Delegates.notNull<Boolean>()
        var hasSwimmingPool by Delegates.notNull<Boolean>()

        fun window(init: HouseBuilder.() -> Int) = apply { window = init() }
        fun door(init: HouseBuilder.() -> Int) = apply { door = init() }
        fun room(init: HouseBuilder.() -> Int) = apply { room = init() }
        fun hasGarden(init: HouseBuilder.() -> Boolean) = apply { hasGarden = init() }
        fun hasSwimmingPool(init: HouseBuilder.() -> Boolean) = apply { hasSwimmingPool = init() }
    }

    companion object {
        fun build(init: HouseBuilder.() -> Unit) = House(HouseBuilder().apply(init))
    }
}

fun main() {
    val house1 = House.build {
        window = 4
        door = 2
        room = 2
        hasGarden = false
        hasSwimmingPool = false
    }

    house1.hasGarden = true

    println(house1)
}

// Output: House(window=4, door=2, room=2, hasGarden=true, hasSwimmingPool=false)

/*


Pros:
    - You can construct objects step-by-step, defer construction steps or run steps recursively.
    - Single Responsibility Principle. You can isolate complex construction code from the business logic of the product

Cons:
    - The overall complexity of the code increases since the pattern requires creating multiple new classes.


What does “.()” mean in Kotlin?

“.()” means that the function can be invoked using object of HouseBuilder class. = {} means that setup parameter is
optional.
So the function takes no parameters and return nothing

High-order functions and lambdas
https://kotlinlang.org/docs/lambdas.html

Higher-order functions in Kotlin
https://agrawalsuneet.medium.com/higher-order-functions-in-kotlin-3d633a86f3d7

 */
