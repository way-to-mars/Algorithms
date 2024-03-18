package lesson2

import kotlin.math.abs

fun main() {
    val n = readln().toInt()   // 1 ≤ n ≤ 100

    val xMoves = IntArray(n) { 0 }
    val shipsByY = IntArray(n) { 0 }

    repeat(n) {
        val (y, x) = readln().split(" ").mapNotNull { it.toIntOrNull()?.run { this - 1 } }.take(2)
        shipsByY[y]++  // looks fun
        (0..<n).forEach { xMoves[it] += abs(x - it) }
    }

    val xSteps = xMoves.min()
    val ySteps = (0..<n).flatMap { y ->
            (1..shipsByY[y]).map { y }
        }
        .withIndex()
        .fold(0) { sum, row -> sum + abs(row.value - row.index) }

    println(xSteps + ySteps)
}