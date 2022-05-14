/*
Design Patterns / Structural Patterns

Adapter

Intent

Adapter is a structural design pattern that allows objects with incompatible interfaces to collaborate.


Problem

Imagine that you’re creating a stock market monitoring app. The app downloads the stock data from multiple sources in
XML format and then displays nice-looking charts and diagrams for the user.

At some point, you decide to improve the app by integrating a smart 3rd-party analytics library.
But there’s a catch: the analytics library only works with data in JSON format.



                            ┌──────────────────────┐
                            │                      │
                            │      Application     │
                            │                      │
                            │    ┌──────────────┐  │
┌────────────┐              │    │ Core Classes │  │               ┌────────────┐
│ Stock Data ├┬─────┐       │    └──┬────────┬──┘  │       ┌──────┬┤ Analytics  │
│ Provider   ││ XML ├───────┼───►   │  XML   │     │       │ JSON ││ Library    │
│            ├┴─────┘       │       └────────┴─────┼──X───►└──────┴┤            │
└────────────┘              │                      │               └────────────┘
                            └──────────────────────┘


You could change the library to work with XML. However, this might break some existing code that relies on the library.
And worse, you might not have access to the library’s source code in the first place, making this approach impossible.

Solution

You can create an adapter. This is a special object that converts the interface of one object so that another object can
understand it.

An adapter wraps one of the objects to hide the complexity of conversion happening behind the scenes. The wrapped object
isn’t even aware of the adapter. For example, you can wrap an object that operates in meters and kilometers with an
adapter that converts all of the data to imperial units such as feet and miles.

Adapters can not only convert data into various formats but can also help objects with different interfaces collaborate.
Here’s how it works:

    1. The adapter gets an interface, compatible with one of the existing objects.
    2. Using this interface, the existing object can safely call the adapter’s methods.
    3. Upon receiving a call, the adapter passes the request to the second object, but in a format and order that the second
    object expects.


                            ┌──────────────────────┐
                            │                      │
                            │      Application     │
                            │                      │
                            │    ┌──────────────┐  │
┌────────────┐              │    │ Core Classes │  │
│ Stock Data ├┬─────┐       │    └──┬────────┬──┘  │
│ Provider   ││ XML ├───────┼───►   │  XML   │     │
│            ├┴─────┘       │       └─┬──────┘     │
└────────────┘              │         │            │
                            │         │            │
                            │         │            │
                            │         │            │
                            │     ┌───▼──┐         │
                            │     │  XML │         │
                            │     ├──────┤         │              ┌────────────┐
                            │  ┌──┴──────┴─┬┬─────┬┤      ┌──────┬┤ Analytics  │
                            │  │XML to JSON││JSON ││      │ JSON ││ Library    │
                            │  │Adapter    ├┴─────┴┼─────►└──────┴┤            │
                            │  └───────────┘       │              └────────────┘
                            │                      │
                            └──────────────────────┘

Sometimes it’s even possible to create a two-way adapter that can convert the calls in both directions.

Let’s get back to our stock market app. To solve the dilemma of incompatible formats, you can create XML-to-JSON
adapters for every class of the analytics library that your code works with directly. Then you adjust your code to
communicate with the library only via these adapters. When an adapter receives a call, it translates the incoming XML
data into a JSON structure and passes the call to the appropriate methods of a wrapped analytics object.


The Adapter pretends to be a round peg, with a radius equal to a half of the square’s diameter (in other words, the
radius of the smallest circle that can accommodate the square peg).

*/

interface Notification {
    fun send(title: String, message: String)
}

class EmailNotification(private val adminEmail: String) : Notification {
    override fun send(title: String, message: String) {
        println("Sent email with title '$title' to '{$adminEmail}' that says '$message'.")
    }
}

class SlackApi(private val login: String, private val apiKey: String) {
    fun logIn() {
        // Send authentication request to Slack web service.
        println("Logged in to a slack account '{$login}' '{$apiKey}' \n")
    }

    fun sendMessage(chatId: String, message: String) {
        // Send message post request to Slack web service.
        println("Posted following message into the '$chatId' chat: '$message'.\n")
    }
}

class SlackNotification(private var slack: SlackApi, private var chatId: String) : Notification {
    override fun send(title: String, message: String) {
        val slackMessage = "#$title # $message"
        slack.logIn()
        slack.sendMessage(chatId, slackMessage)
    }
}

fun clientCode(notification: Notification) {
    notification.send(
        "Website is down!",
        "<strong style='color:red;font-size: 50px;'>Alert!</strong> \n Our website is not responding. Call admins and bring it up!"
    )
}

fun main() {
    println("Client code is designed correctly and works with email notifications:\n")
    val emailNotification = EmailNotification("developers@example.com")
    clientCode(emailNotification)
    println("\n\n")

    println("The same client code can work with other classes via adapter:\n")
    val slackApi = SlackApi("example.com", "XXXXXXXX")
    val slackNotification = SlackNotification(slackApi, "Example.com Developers")
    clientCode(slackNotification)
}

/*
Applicability

Use the Adapter class when you want to use some existing class, but its interface isn’t compatible with the rest of
your code.

The Adapter pattern lets you create a middle-layer class that serves as a translator between your code and a legacy
class, a 3rd-party class or any other class with a weird interface.

Use the pattern when you want to reuse several existing subclasses that lack some common functionality that can’t be
added to the superclass.

You could extend each subclass and put the missing functionality into new child classes. However, you’ll need to
duplicate the code across all of these new classes, which smells really bad.

The much more elegant solution would be to put the missing functionality into an adapter class. Then you would wrap
objects with missing features inside the adapter, gaining needed features dynamically. For this to work, the target
classes must have a common interface, and the adapter’s field should follow that interface. This approach looks very
similar to the Decorator pattern.

How to Implement

Make sure that you have at least two classes with incompatible interfaces:

A useful service class, which you can’t change (often 3rd-party, legacy or with lots of existing dependencies).
One or several client classes that would benefit from using the service class.
Declare the client interface and describe how clients communicate with the service.

Create the adapter class and make it follow the client interface. Leave all the methods empty for now.

Add a field to the adapter class to store a reference to the service object. The common practice is to initialize this
field via the constructor, but sometimes it’s more convenient to pass it to the adapter when calling its methods.

One by one, implement all methods of the client interface in the adapter class. The adapter should delegate most of the
real work to the service object, handling only the interface or data format conversion.

Clients should use the adapter via the client interface. This will let you change or extend the adapters without
affecting the client code.

Pros:

    - Single Responsibility Principle. You can separate the interface or data conversion code from the primary business
    logic of the program.
    - Open/Closed Principle. You can introduce new types of adapters into the program without breaking the existing
    client code, as long as they work with the adapters through the client interface.

Cons:
    - The overall complexity of the code increases because you need to introduce a set of new interfaces and classes.
    Sometimes it’s simpler just to change the service class so that it matches the rest of your code.

*/
