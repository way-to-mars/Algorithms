package lesson2

import javax.swing.JToolBar.Separator

fun main() {
    val n = readln().toInt()
    val berries = (1..n).map {
        val (a, b) = readln().split(" ").map(String::toInt)
        Berry(it, a, b)
    }.sortedDescending()

    berries.joinToString(separator = "\n").also(::println)

    var maxHeight = 0L
    var pos = 0L
    berries.asSequence().forEach {
        val upper = pos + it.a
        pos = upper - it.b
        if (upper > maxHeight) maxHeight = upper
    }

    println(maxHeight)
    berries.asSequence().map { it.index }.joinToString(separator = " ").also(::println)
}

data class Berry(val index: Int, val a: Int, val b: Int) : Comparable<Berry> {
    override fun compareTo(other: Berry): Int {
        if (a - b < 0 && other.a - other.b < 0) return  a - other.a
        if (a - b > other.a - other.b) return 1
        if (a - b < other.a - other.b) return -1
        if (a > b) return -1
        if (a < b) return 1
        return 0
    }
    override fun toString() = "($index, $a, $b -> ${a-b})"
}