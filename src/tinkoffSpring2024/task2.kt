package tinkoffSpring2024

import examples.Array2D

fun main() {
    val (n, m) = readln().split(" ").map(String::toInt)
    val matrix = Array2D(n, m)
    val range = 0..m - 1

    repeat(n) { x ->
        val line = readln().split(" ")
        range.forEach { y ->
            matrix[x, y] = line[y].toLong()
        }
    }

    range.forEach {
        matrix.columnReversed(it).joinToString(separator = " ").also(::println)
    }
}

class Array2D(val x: Int, val y: Int) {
    private val data = LongArray(x * y) { 0L }

    operator fun set(i: Int, j: Int, value: Long) {
        data[i + x * j] = value
    }

    fun columnReversed(i: Int): LongArray {
        return LongArray(x) { data[(x - it - 1) + i * x] }
    }
}