/*
Singleton Pattern
Singleton is a creational design pattern that lets you ensure that a class has only one instance while providing a
global access point to this instance.

Problem:

Why would anyone want to control how many instances a class has? The most common reason for this is to control access to
some shared resource — for example, a database or a file.

Here’s how it works: imagine that you created an object, but after a while decided to create a new one. Instead of
receiving a fresh object, you’ll get the one you already created.

Note that this behaviour is impossible to implement with a regular constructor since a constructor call must always
return a new object by design.

Solution:

All implementations of the Singleton have these two steps in common:

Make the default constructor private, to prevent other objects from using the new operator with the Singleton class.
Create a static creation method that acts as a constructor. Under the hood, this method calls the private constructor to
create an object and saves it in a static field. All following calls to this method return the cached object.


                    ┌───────────────────────────────┐
                    │  Singleton                    ◄────────┐
┌───────────┐       ├──────────────────────────────┬┐        │
│           │       │  - instance: Singleton       │┼────────┘
│  Client   ├──────►├──────────────────────────────┴┤
│           │       │  - Singleton                  │
└───────────┘       │  - getInstance(): Singleton   │
                    │                               │
                    └───────────────────────────────┘


                    Singleton 1.0

*/

class Singleton private constructor(value: String) {
    var value: String

    companion object {
        private var instance: Singleton? = null
        fun getInstance(value: String): Singleton? {
            if (instance == null) {
                instance = Singleton(value)
            }
            return instance
        }
    }

    init {
        // The following code emulates slow initialization.
        try {
            Thread.sleep(1000)
        } catch (ex: InterruptedException) {
            ex.printStackTrace()
        }
        this.value = value
    }
}

fun main() {
    val singleton: Singleton? = Singleton.getInstance("FOO")
    val anotherSingleton: Singleton? = Singleton.getInstance("BAR")
    println(singleton?.value)
    println(anotherSingleton?.value)
}

/* Output
FOO
FOO


Pros:

    - You can be sure that a class has only a single instance.
    - The singleton object is initialized only when it’s requested for the first time.

Cons:

    - It may be difficult to unit test the client code of the Singleton because many test frameworks rely on inheritance
    when producing mock objects. Since the constructor of the singleton class is private and overriding static methods
    is impossible in most languages, you will need to think of a creative way to mock the singleton. Or just don’t write
    the tests.

    Or don’t use the Singleton pattern.

 */
