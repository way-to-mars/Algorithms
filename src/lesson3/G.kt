package lesson3

// G. Построить квадрат
fun main() {
    val n = readln().toInt()  // N (1 ≤ N ≤ 2000) — количество точек.
    val points: Set<Pair<Int, Int>> =  // по два числа xi, yi (-10^8 ≤ xi, yi ≤ 10^8) — координаты точек.
        (1..n).map { readln().split(" ").run { this[0].toInt() to this[1].toInt() } }.toSet()

    val pts = pointsMissing(points)
    println(pts.size)
    pts.forEach { println("${it.first} ${it.second}") }
}

fun pointsMissing(points: Set<Pair<Int, Int>>): List<Pair<Int, Int>> {
    infix fun Boolean.plus(other: Boolean) = when {
        this && other -> 2
        this || other -> 1
        else -> 0
    }

    val result = mutableListOf<Pair<Int, Int>>()
    points.forEach { pt1 ->
        points.asSequence()
            .filter { it != pt1 }.forEach { pt2 ->
                val dx = pt2.first - pt1.first
                val dy = pt2.second - pt1.second
                val pt3 = pt2.first - dy to pt2.second + dx  // pt3 = (pt2.x - dy, pt2.y + dx)
                val pt4 = pt1.first - dy to pt1.second + dx  // pt4 = (pt1.x - dy, pt1.y + dx)
                val count = points.contains(pt3) plus points.contains(pt4)  // [0, 1, 2]
                when (count) {
                    2 -> return emptyList()
                    1 -> if (result.size != 1) {
                        result.clear()
                        if (points.contains(pt3)) result.add(pt4)
                        else result.add(pt3)
                    }
                    0 -> if (result.isEmpty()) {
                        result.add(pt3)
                        result.add(pt4)
                    }
                }
            }
    }
    return result.toList()
}

