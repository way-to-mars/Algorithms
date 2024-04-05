package examples

fun main() {

    val x = maxOf(1,4,7,4,2)

    val a = listOf(9, 9, 8, 1, 2, 3, 5, 9)
    val b = listOf(1, 1, 2, 3, 9, 6, 4, 5, 7)


    longestCommonSubsequence(a, b).also { println(it) }
}

fun <T> longestCommonSubsequence(a: List<T>, b: List<T>): Int {
    val f = Array2D(a.size + 1, b.size + 1, 0)
    for (i in 1..a.size)
        for (j in 1..b.size)
            if (a[i - 1] == b[j - 1])
                f[i, j] = 1 + f[i - 1, j - 1]
            else
                f[i, j] = maxOf(f[i - 1, j], f[i, j - 1])
    println(f)
    return f.last()
}