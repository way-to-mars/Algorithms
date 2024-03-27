package lesson3

fun main() {
    val n = readln().toInt()    // 1..1000
    val figureA = (1..n).map { Stick.fromInput(readln()) }
    val figureB = (1..n).map { Stick.fromInput(readln()) }

    val commonVectors = figureA.map(Stick::asVector).toSet() intersect figureB.map(Stick::asVector).toSet()
    if (commonVectors.isEmpty()) {
        println(n)
        return
    }
    val maxMatches = figureA.fold(mutableMapOf<DeltaSticks, Int>()){ map, stickA ->
        figureB.forEach { stickB ->
            val delta = stickA minus stickB
            map[delta] = map.getOrDefault(delta, 0) + 1
        }
        map
    }.values.max()

    println(n - maxMatches)
}

data class Point(val x: Int = 0, val y: Int = 0) {
    infix fun minus(other: Point) = Point(x - other.x, y - other.y)
}

class Stick(x1: Int, y1: Int, x2: Int, y2: Int) {
    private val startPoint: Point
    private val endPoint: Point
    init {
        val pt1 = Point(x1, y1)
        val pt2 = Point(x2, y2)

        if (x1 < x2 || (x1 == x2 && y1 < y2)) {
            startPoint = pt1
            endPoint = pt2
        } else {
            startPoint = pt2
            endPoint = pt1
        }
    }
    companion object {
        fun fromInput(str: String): Stick {
            val (x1, y1, x2, y2) = str.split(" ").map(String::toInt)
            return Stick(x1, y1, x2, y2)
        }
    }
    fun asVector() = endPoint minus startPoint
    infix fun minus(other: Stick) = DeltaSticks(other.startPoint minus startPoint, other.endPoint minus endPoint)
}

data class DeltaSticks(val pt1: Point, val pt2: Point)