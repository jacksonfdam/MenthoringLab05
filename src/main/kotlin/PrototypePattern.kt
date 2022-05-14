/*
Prototype Pattern

Prototype design pattern lets you copy existing objects without making your code dependent on their classes.

Problem:

Let’s say you have an object and you want to copy that. How would you do it? First, you have to create a new object of
the same class. Then you have to go through all the fields of the original object and copy their values over to the
new object.

Solution:

The Prototype pattern delegates the cloning process to the actual objects that are being cloned. The pattern declares a
common interface for all objects that support cloning. This interface lets you clone an object without coupling your
code to the class of that object. Usually, such an interface contains just a single clone method.

                 ┌─────────────────────┐
                 │  Shape              │
                 ├─────────────────────┤
                 │  - x                │
                 │  - y                ├─────┐
          ┌──────┤  - color            │     │
          │      ├─────────────────────┤     │
          │      │  - shape(Source)    │     │
          │      │  - clone()          │     │
          │      └─────────────────────┘     │
          │                                  │
┌─────────▼───────────┐             ┌────────▼─────┐
│    Rectangle        │             │ Circle       │
├─────────────────────┤             ├──────────────┤
│    - width          │             │ - radius     │
│    - height         │             ├──────────────┤
│                     │             │ - Circle()   │
│    - Rectangle()    │             │ - clone()    │
│    - clone()        │             └──────────────┘
└─────────────────────┘

                    Prototype Pattern 1.0



 */

class Shape constructor(
    var xCoordinate: Int = 0,
    var yCoordinate: Int = 0,
    var color: String = ""
) : Cloneable {

    fun cloneTo(): Shape? {
        try {
            val copy: Shape = super.clone() as Shape
            copy.xCoordinate = this.xCoordinate
            copy.yCoordinate = this.yCoordinate
            copy.color = this.color
            return copy
        } catch (e: CloneNotSupportedException) {
            e.printStackTrace()
        }
        return null
    }
}

fun main() {
    val original: Shape = Shape().apply {
        xCoordinate = 10
        yCoordinate = 3
        color = "red"
    }

    val copy = original.cloneTo()?.apply {
        xCoordinate = 14
        yCoordinate = 80
        color = "blue"
    }

    println(copy!!.xCoordinate)

    // Output: 14
}

/*
The above code explains the proper usage of the Cloneable interface to make the Object.clone() method legal.
Classes that implement this interface should override the Object.clone() method (which is protected) so that it can be
invoked.

Note: another way you can simply use original.copy(xCoordinate=10) to clone objects.

Pros:
    - You can clone objects without coupling them to their concrete classes.
    - You can produce complex objects more conveniently.
    - You get an alternative to inheritance when dealing with configuration presets for complex objects.

Cons:
    - Cloning complex objects that have circular references might be very tricky.

 */
