/*
Design patterns


Design patterns are typical solutions to common problems in software design.
Each pattern is a blueprint that you can customize to solve a particular design problem in your code.

What is a Design pattern?
In software engineering, a design pattern is a general repeatable solution to a commonly occurring problem
in software design. A design pattern isn’t a finished design that can be transformed directly into code.
It is a description or template for how to solve a problem that can be used in many different situations.

Patterns are often confused with algorithms because both concepts describe typical solutions to some known problems.
While an algorithm always defines a clear set of actions that can achieve some goal, a pattern is a more high-level
description of a solution. The code of the same pattern applied to two different programs may be different.

Classification of patterns:

 - Creational patterns provide object creation mechanisms that increase flexibility and reuse of existing code.
 - Structural patterns explain how to assemble objects and classes into larger structures while keeping the structures
 flexible and efficient.
 - Behavioural patterns take care of effective communication and the assignment of responsibilities between objects.
Let’s start exploring types of patterns…



Creational design pattern

1. Factory Method
Factory Method is a creational design pattern that provides an interface for creating objects in a superclass but allows
subclasses to alter the type of objects that will be created.

Problem:

Imagine that you’re creating a delivery management application. The first version of your app can only handle
transportation by trucks, so the bulk of your code lives inside the Truck class.

After a while, your app becomes pretty popular worldwide. Each day you receive dozens of requests from Air
transportation companies to incorporate worldwide deliveries.

Challenge:

But how about the code? At present, most of your code is coupled to the Truck class. Adding transport by Air into the
app would require making changes to the entire codebase. Moreover, if later you decide to add another type of
transportation to the app, you will probably need to make all of these changes again.

As a result, you will end up with pretty nasty code, riddled with conditionals that switch the app’s behaviour depending
on the class of transportation objects.

Solution:

The below example illustrates how the Factory Method can be used for creating delivery transport without coupling the
transportation type into the main class.


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

(made with https://asciiflow.com/)


Let’s jump into the implementation,

 */

enum class DeliveryType {
    ROAD,
    AIR;
}

interface Transport {
    fun planDelivery(): String
    fun getTransportType(): DeliveryType
}

class Truck : Transport {
    override fun planDelivery(): String {
        return "Inside Road Transport"
    }

    override fun getTransportType() = DeliveryType.ROAD
}

class Air : Transport {
    override fun planDelivery(): String {
        return "Inside Air Transport"
    }

    override fun getTransportType() = DeliveryType.AIR
}

fun scheduleDelivery(deliveryType: DeliveryType): Transport {
    return when (deliveryType) {
        DeliveryType.ROAD -> Truck()
        DeliveryType.AIR -> Air()
    }
}

fun main() {
    val delivery = scheduleDelivery(DeliveryType.ROAD)
    println(delivery.planDelivery())
}

/*

So using the Factory method pattern you can decouple your logic to specific classes, just based on the delivery type you
can easily instantiate the respective class and invoke a particular method.

Pros:

    1. You avoid tight coupling between the creator and the concrete products.
    2. Single Responsibility Principle. You can move the product creation code into one place in the program,
    making the code easier to support.
    3. Open/Closed Principle. You can introduce new types of products into the program without breaking the existing
    client code.

Cons:
    1. The code may become more complicated since you need to introduce a lot of new subclasses to implement the pattern.
    2. The best-case scenario is when you’re introducing the pattern into an existing hierarchy of creator classes.


 */