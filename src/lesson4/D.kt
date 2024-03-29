package lesson4

fun binarySearch(a: List<Int>, b: List<Int>, n: Int, m: Int, w: Int, left: Int, right: Int): Int {
    var l = left
    var r = right
    while (l < r) {
        val mid = (l + r) / 2
        if (length(a, n, mid) < length(b, m, w - mid))
            r = mid
        else
            l = mid + 1
    }
    return r - 1
}

fun paperLength(a: List<Int>, b: List<Int>, n: Int, m: Int, width1: Int, width2: Int): Int {
    return maxOf(length(a, n, width1), length(b, m, width2))
}
fun length(list: List<Int>, n: Int, w: Int): Int {
    var len = 1
    var room = w
    var i = 0
    while (i < n) {
        if (list[i] > w) {
            return -1
        }
        if (list[i] <= room) {
            room -= (list[i] + 1)
        } else {
            i--
            len++
            room = w
        }
        i++
    }
    return len
}

fun main() {
    val (w, n, m) = readln().split(" ").map(String::toInt)

    val a = readln().split(" ").map(String::toInt)
    val b = readln().split(" ").map(String::toInt)

    val maxA = a.max()
    val maxB = b.max()
    if (maxA + maxB == w) {
        println(paperLength(a, b, n, m, maxA, maxB))
        return
    }

    var l = maxB
    var r = w - maxA
    var res = binarySearch(b, a, m, n, w, l, r)
    if (res == 0) res = 1
    val case1 = paperLength(a, b, n, m, w - res, res)
    l = maxA
    r = w - maxB
    res = binarySearch(a, b, n, m, w, l, r)
    if (res == 0) res = 1
    val case2 = paperLength(a, b, n, m, res, w - res)
    println(minOf(case1, case2))
}