package dynamicAlgos

import examples.Array2D
import printLine

fun main() {
    infix fun <T> List<T>.lcs(other: List<T>) = longestCommonSubsequence(this, other)
    val a = listOf(3, 1, 2, 0, 5, 17, 2)
    val b = listOf(1, 2, 3, 5, 6, 17, 1, 2, 0, 5, 5, 6, 7, 8, 9)
    val c = listOf(7, 2, 1, 3, 8, 4, 9, 1, 2, 6, 5, 9, 3, 8, 1)

    (a lcs b).printLine()
    longestCommonSubstring(a, b).printLine()

    (c lcs c.sorted()).printLine()
    lis(c)
}

fun <T> longestCommonSubsequence(a: List<T>, b: List<T>): List<T> {
    if (a.isEmpty() || b.isEmpty()) return emptyList()
    val f = Array2D(a.size + 1, b.size + 1, init = 0)
    for (i in 1..a.size)
        for (j in 1..b.size)
            if (a[i - 1] == b[j - 1])
                f[i, j] = 1 + f[i - 1, j - 1]
            else
                f[i, j] = maxOf(f[i - 1, j], f[i, j - 1])
    val size = f.last()
    val res = mutableListOf<T>()
    var i = a.size
    var j = b.size
    while (res.size < size) {
        while (f[i - 1, j] == f[i, j]) i--
        while (f[i, j - 1] == f[i, j]) j--
        i--
        j--
        res.add(0, a[i])
    }
    return res
}

fun <T> longestCommonSubsequenceLength(a: List<T>, b: List<T>): Int {
    if (a.isEmpty() || b.isEmpty()) return 0
    val f = Array2D(a.size + 1, b.size + 1, init = 0)
    for (i in 1..a.size)
        for (j in 1..b.size)
            if (a[i - 1] == b[j - 1])
                f[i, j] = 1 + f[i - 1, j - 1]
            else
                f[i, j] = maxOf(f[i - 1, j], f[i, j - 1])
    return f.last()
}

fun <T> longestCommonSubstring(a: List<T>, b: List<T>): List<T> {
    if (a.isEmpty() || b.isEmpty()) return emptyList()
    val f = Array2D(a.size + 1, b.size + 1, init = 0)
    for (i in 1..a.size)
        for (j in 1..b.size)
            if (a[i - 1] == b[j - 1])
                f[i, j] = 1 + f[i - 1, j - 1]
            else
                f[i, j] = 0
    fun subString(linearIndex: Int): List<T> {
        val (i, j) = f.index(linearIndex)
        val size = f[i, j]
        return a.subList(i - size, i)  // i - 1
    }

    val linearIndex = f.asSequence().withIndex().maxBy { it.value }.index

    return subString(linearIndex)
}

/// Наибольшая возрастающая подпоследовательность
fun lis(a: List<Int>) {
    val d = Array(a.size) { -1 }
    val p = Array(a.size) { -1 }  // keeps indexes
    for (i in a.indices) {
        for (j in 0..<i) {
            if (a[j] < a[i])
                if (1 + d[j] > d[i]) {
                    d[i] = 1 + d[j]
                    p[i] = j
                }
        }
    }

    var ans = d[0]
    var pos = 0

    for (i in 1..<d.size) {
        if (d[i] > ans) {
            ans = d[i]
            pos = i
        }
    }
    println(ans)

    val path = mutableListOf<Int>()
    while (pos != -1) {
        path.add(0, pos)
        pos = p[pos]
    }
    println(path.joinToString(separator = ", ") { a[it].toString() })
}

