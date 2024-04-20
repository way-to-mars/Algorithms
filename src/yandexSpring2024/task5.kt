package yandexSpring2024

import printLine

fun main() {
    val (n, l, r) = readln().split(" ").map(String::toInt)
    val input = readln().toCharArray()

    fun weight(i: Int, len: Int): Int {
        if (i + len > n) return -1
        var min = input[i].code
        var max = min
        (1..len-1).forEach {
            val cur = input[i + it].code
            if (cur > max) max = cur
            else if (cur < min) min = cur

        }
        println("$i $len -> ${max - min}")
        return max - min
    }

    fun rec(sum: Int, start: Int): Int {
        val restSize = n - start
        if (restSize == 0){
            println("sum=" + sum)
            return sum
        }
        if (restSize < l) return -1
        return (l..r).maxOf {
            rec(
                sum + weight(start, it),
                start + it
            )
        }
    }

    rec(0, 0).printLine()
}

