package lesson3

import kotlin.math.max

fun main() {
    val n = readln().toIntOrNull() ?: 0
    val dict = mutableMapOf<Int, Int>()
    val numbers = readln().split(" ").asSequence()
        .forEach { str ->
            val v = str.toInt()
            dict[v] = dict.getOrDefault(v, 0) + 1
        }
    var maxPair = 0
    dict.forEach { (k, v) ->
        val pair = max(dict.getOrDefault(k - 1, 0), dict.getOrDefault(k + 1, 0)) + v
        if (pair > maxPair) maxPair = pair
    }
    println(n - maxPair)
}