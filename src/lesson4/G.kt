package lesson4

fun main() {
    val (n, m) = readln().split(" ").map(String::toInt)
    val field = (1..n).map {
        readln().mapNotNull {
            when (it) {
                '#' -> 1
                '.' -> 0
                else -> null
            }
        }
    }
    binarySearch(field.prefixes(), n, m).also(::println)
}


fun List<List<Int>>.prefixes(): Array<IntArray> {
    val rows = this.size
    val columns = this[0].size
    val res = Array(rows + 1) { IntArray(columns + 1) { 0 } }
    for (j in 0..<columns)
        for (i in 0..<rows)
            res[i + 1][j + 1] = res[i][j + 1] + res[i + 1][j] - res[i][j] + this[i][j]
    return res
}

fun crossArea(x: Int, y: Int, k: Int, prefixes: Array<IntArray>): Int {
    fun sum(x1: Int, y1: Int, x2: Int, y2: Int) =
        prefixes[x2][y2] - prefixes[x1][y2] - prefixes[x2][y1] + prefixes[x1][y1]

    return sum(x + k, y, x + 2 * k, y + k) +
            sum(x, y + k, x + 3 * k, y + 2 * k) +
            sum(x + k, y + 2 * k, x + 2 * k, y + 3 * k)
}

fun func(k: Int, prefixes: Array<IntArray>, n: Int, m: Int): Boolean {
    val area = 5 * k * k
    for (x in 0..n - 3 * k)
        for (y in 0..m - 3 * k)
            if (crossArea(x, y, k, prefixes) == area)
                return true
    return false
}

fun binarySearch(prefixes: Array<IntArray>, n: Int, m: Int): Int {
    fun min(a: Int, b: Int) = if (a < b) a else b
    var l = 1
    var r = min(n, m) / 3
    while (l < r) {
        val mid = (l + r + 1) / 2
        if (func(mid, prefixes, n, m)) {
            l = mid
        } else {
            r = mid - 1
        }
    }
    return r
}