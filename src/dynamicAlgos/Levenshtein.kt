package dynamicAlgos

import examples.Array2D

fun main() {
    val a = "австралия"
    val b = "австрия"
    levenstein(a.toList(), b.toList()).printLine()
}


/**
 * Расстояние Левенштейна, или редакционное расстояние, — метрика cходства между двумя строковыми последовательностями.
 * Чем больше расстояние, тем более различны строки. Для двух одинаковых последовательностей расстояние равно нулю.
 * По сути, это минимальное число односимвольных преобразований (удаления, вставки или замены), необходимых, чтобы
 * превратить одну последовательность в другую.
 */
fun <T : Comparable<T>> levenstein(a: List<T>, b: List<T>): Int {
    fun minOf3(a: Int, b: Int, c: Int) = if (a < b && a < c) a else if (b < c) b else c
    val m = { i: Int, j: Int -> if (a[i - 1] == b[j - 1]) 0 else 1 }

    val d = Array2D<Int>(a.size + 1, b.size + 1) { i, j ->
        when {
            i == 0 -> j
            j == 0 -> i
            else -> 0
        }
    }
    println(d)
    println()
    for (i in 1..a.size)
        for (j in 1..b.size)
            d[i, j] = minOf3(
                d[i, j -1] + 1,
                d[i - 1, j] + 1,
                d[i - 1, j - 1] + m(i, j)
            )
    println(d)
    return d.last()
}