package tinkoffSpring2024

fun main() {
    val n = readln().toInt()  // 1..10^4
    val forest = Forest(n)
    repeat(n) { forest.add(readln()) }
    forest.solve()
}

class Forest(val size: Int) {
    enum class Cell {
        Grass, Mushroom, Bushes,
    }

    val data = mutableListOf<List<Cell>>()
    fun add(str: String) { // reversed order
        data.addFirst(
            str.map {
                when (it) {
                    'W' -> Cell.Bushes
                    'C' -> Cell.Mushroom
                    else -> Cell.Grass
                }
            }
        )
    }

    fun solve() {
        fun max2(a: Int, b: Int) = if (a > b) a else b
        fun max3(a: Int, b: Int, c: Int) = if (a > b && a > c) a else if (b > c) b else c

        var prev = IntArray(3) { 0 }
        data.forEach { three ->
            val cur = IntArray(3)
            cur[0] = when (three[0]) {
                Cell.Bushes -> 0
                Cell.Mushroom -> max2(prev[0], prev[1]) + 1
                Cell.Grass -> max2(prev[0], prev[1])
            }
            cur[1] = when (three[1]) {
                Cell.Bushes -> 0
                Cell.Mushroom -> max3(prev[0], prev[1], prev[2]) + 1
                Cell.Grass -> max3(prev[0], prev[1], prev[2])
            }
            cur[2] = when (three[2]) {
                Cell.Bushes -> 0
                Cell.Mushroom -> max2(prev[1], prev[2]) + 1
                Cell.Grass -> max2(prev[1], prev[2])
            }
            prev = cur
        }
        println(max3(prev[0], prev[1], prev[2]))
    }
}