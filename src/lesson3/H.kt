package lesson3

import kotlin.math.abs

fun main() {
    val n = readln().toInt()    // 1..1000
    val figureA = (1..n).map {
        val (x1, y1, x2, y2) = readln().split(" ").map(String::toInt)
        Stick(x1, y1, x2, y2)
    }
    val figureB = (1..n).map {
        val (x1, y1, x2, y2) = readln().split(" ").map(String::toInt)
        Stick(x1, y1, x2, y2)
    }

    val commonVectors = figureA.map(Stick::asVector).toSet() intersect figureB.map(Stick::asVector).toSet()

    if (commonVectors.isEmpty()){
        println(n)
        return
    }

    val deltaMap = mutableMapOf<DeltaSticks, Int>()

    figureA.forEach { stickA ->
        figureB.forEach { stickB ->
            val delta = stickA delta stickB
            deltaMap[delta] = deltaMap.getOrDefault(delta, 0) + 1
        }
    }

    val maxMatches = deltaMap.values.max()
    println(n - maxMatches)

}

data class Point(val x: Int = 0, val y: Int = 0) {
    infix fun minus(other: Point) = Point(x - other.x, y - other.y)
}

class Stick(x1: Int, y1: Int, x2: Int, y2: Int) {
    val pt1 = Point(x1, y1)
    val pt2 = Point(x2, y2)

    fun asVector() = pt2 minus pt1
    infix fun delta(other: Stick) = DeltaSticks(other.pt1 minus pt1 , other.pt2 minus pt2)
}

data class DeltaSticks(val pt1: Point, val pt2: Point)
