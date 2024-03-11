package lesson2

fun main() {
    val n = readln().toInt()
    val cells = (1..n).map { readln().split(" ").map(String::toInt).run { this[0] to this[1] } }

    fun left(pair: Pair<Int, Int>) = if (cells.contains(pair.first - 1 to pair.second)) 1 else 0
    fun up(pair: Pair<Int, Int>) = if (cells.contains(pair.first to pair.second - 1)) 1 else 0
    fun right(pair: Pair<Int, Int>) = if (cells.contains(pair.first + 1 to pair.second)) 1 else 0
    fun down(pair: Pair<Int, Int>) = if (cells.contains(pair.first to pair.second + 1)) 1 else 0

    cells.fold(0) { sum, cell -> sum + 4 - left(cell) - right(cell) - up(cell) - down(cell) }.also(::println)
}