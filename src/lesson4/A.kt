package lesson4

fun main() {
    readln().toInt()  // n
    val taskA = TaskA()
    val k = readln().toInt()
    (1..k).map {
        val (l, r) = readln().split(" ").map(String::toInt)
        taskA.calc(l, r)
    }.joinToString(separator = " ").also(::println)
}

class TaskA() {
    val values = readln().split(" ").map(String::toInt).sorted().toIntArray()

    fun calc(left: Int, right: Int): Int {
        val last = right(right)
        val first = left(left)
        if (first > last) return 0
        if (first == last && values[first] !in left..right) return 0
        return last + 1 - first
    }

    private fun left(value: Int): Int {
        if (values.isEmpty() || value <= values.first()) return 0
        var l = 0
        var r = values.lastIndex
        do {
            val m = (l + r) / 2
            if (values[m] >= value)
                r = m
            else
                l = m + 1
        } while (l < r)
        return r
    }

    private fun right(value: Int): Int {
        if (values.isEmpty() || value >= values.last()) return values.lastIndex
        var l = 0
        var r = values.lastIndex
        do {
            val m = (l + r + 1) / 2
            if (values[m] > value)
                r = m - 1
            else
                l = m
        } while (l < r)
        return r
    }
}

/*
5
2 3 8 7 5
1
3 3

5
26 26 26 26 26
4
1 10
2 9
3 4
2 2
 */