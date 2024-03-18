package lesson2

fun main() {
    val t = readln().toInt()  // (1 ≤ t ≤ 1000)
    (1..t).map {
        val n = readln().toInt()    // (1 ≤ n ≤ 10^5)
        val arr = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(n)   // a1, a2, …, an (1 ≤ ai ≤ n)

        val lengths = mutableListOf<Int>()
        var curLen = 1
        var curMin = arr[0]

        for (i in 1..n) {
            if (i == n) {  // out of range
                lengths.add(curLen)  // add last length to result
                break
            }
            val ai = arr[i]
            if (ai < curMin) curMin = ai
            if (curMin < curLen + 1) {
                lengths.add(curLen) // add previous length to result
                curMin = ai
                curLen = 1
            } else { curLen++ }
        }
        lengths
    }.forEach {
        println(it.size)
        println(it.joinToString(separator = " "))
    }
}