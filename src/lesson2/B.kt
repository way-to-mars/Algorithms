package lesson2

import kotlin.math.max
import kotlin.math.min

fun main() {
    val (n, k) = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(2)
    val prices = readln().split(" ").mapNotNull { it.toIntOrNull() }
    if (n == 1) {
        println(0)
        return
    }
    fun subMax(left: Int, len: Int): Long {
        var curSum = 0L
        var maxSumRange = 0L
        for (index in left..<left + len) {
            curSum += prices[index] - prices[index - 1]
            if (curSum > maxSumRange) maxSumRange = curSum
            if (curSum < 0) curSum = 0
        }
        return maxSumRange
    }
    val steps = max(n - k, 1)
    val size = min(n - 1, k)
    (1..steps).maxOf { subMax(it, size) }.also(::println)
}
