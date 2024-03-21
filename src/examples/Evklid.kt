package examples

import kotlin.math.max
import kotlin.math.min

// Алгоритм Евклида
// алгоритм для нахождения наибольшего общего делителя двух целых чисел
fun main() {
    greatestCommonDivisor(210000, 280000000).also(::println)
    greatestCommonDivisor2(210000, 280000000).also(::println)
}

tailrec fun greatestCommonDivisor(a: Int, b: Int): Int {
    require(a > 0 && b > 0)
    if (a == b) return a
    val x1 = max(a, b)
    val x2 = min(a, b)
    return greatestCommonDivisor(x1 - x2, x2)
}

tailrec fun greatestCommonDivisor2(a: Int, b: Int): Int = if (b == 0) a else greatestCommonDivisor2(b, a % b)
