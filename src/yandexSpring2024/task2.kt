package yandexSpring2024

fun main() {
    val route = mutableMapOf((0 to 0) to 1)
    var coord = 0 to 0
    readln().forEach { ch ->
        when (ch) {
            'U' -> coord = Pair(coord.first, coord.second + 1)
            'D' -> coord = Pair(coord.first, coord.second - 1)
            'L' -> coord = Pair(coord.first - 1, coord.second)
            'R' -> coord = Pair(coord.first + 1, coord.second)
        }
        route[coord] = route.getOrDefault(coord,0) + 1
    }
    route.values.count { it > 1 }.printLine()
}

fun Any?.printLine(): Any? {
    println(this)
    return this
}

