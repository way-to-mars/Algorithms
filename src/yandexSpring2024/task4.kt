package yandexSpring2024

import printLog


fun main() {
    val (n, k) = readln().split(" ").map(String::toInt)
    val array = readln().split(" ").asSequence().map(String::toInt).sorted().toMutableList()
    if (array.last() > k) println("Impossible")
    else if (2 * array.first() > k) println(n)
    else {
        var counter = 0
        while (array.isNotEmpty()) {
            val cur: Int = array.last()
            if (2 * cur <= k || 2 * array.first() > k) break
            val leftIndex = firstTrue(0, array.size - 1) { (array[it] + cur) > k } - 1
            if (leftIndex >= 0) array.removeAt(leftIndex)
            array.removeLast()
            counter++
        }
        if (array.isNotEmpty()) {
            if (2 * array.last() <= k) // all pairs
                counter += (array.size + 1) / 2
            else    // all singles
                counter += array.size
        }
        println(counter)
    }
}

inline fun firstTrue(left: Int, right: Int, func: (Int) -> Boolean): Int {
    if (left > right) return -1

    var l = left
    var r = right
    while (l < r) {
        val m = (l + r) / 2
        if (func(m))
            r = m
        else
            l = m + 1
    }
    return l
}