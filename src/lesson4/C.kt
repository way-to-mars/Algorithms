package lesson4

fun main() {
    val (_, m) = readIntegers()  // 1 .. 2⋅10^5
    val orcsList = readIntegers()    // asc.sorted, 1 .. 10^9
    val sumsArray = sums(orcsList)
    (1..m).joinToString(separator = "\n") {
        val input = readln().split(" ")
        val l = input[0].toInt()        // кол-во полков [1..n]
        val s = input[1].toLong()       // сумма орков в этих полках [1..2⋅10^16]
        binarySearch(sumsArray, l, s).toString()
    }.also(::println)
}

fun readIntegers() = readln().split(" ").map(String::toInt)

fun sums(list: List<Int>): LongArray {
    val res = LongArray(list.size + 1) { 0 }
    var i = 0
    list.forEach {
        val prev = res[i]
        i++
        res[i] = prev + it
    }
    return res
}

fun binarySearch(array: LongArray, count: Int, sum: Long): Int {
    fun func(i: Int) = array[i + count - 1] - array[i - 1]  // indexes from 1
    var l = 1
    var r = array.size - count
    if (func(l) > sum || func(r) < sum) return -1
    while (l < r) {
        val m = (l + r + 1) / 2
        val curSum = func(m)
        if (curSum == sum) return m
        if (curSum < sum) {
            l = m + 1
        } else {
            r = m - 1
        }
    }
    if (func(l) == sum) return l
    return -1
}

/*
5 10
570703232 570703232 570703232 570703232 570703232
2 687772523
1 570703232
1 570703232
1 570703232
1 570703232
1 570703232
2 1141406464
1 570703232
1 570703232
3 1712109696
 */