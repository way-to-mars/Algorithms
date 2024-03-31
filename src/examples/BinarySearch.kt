package examples

import examples.BinarySearch.Companion.sliceBinSearch
import kotlin.math.pow

fun main() {
    val values = arrayOf(3, 5, 7, 7, 9, 11, 12, 12, 12, 15)

    BinarySearch.lastFalse(0, values.size - 1) { values[it] >= 7 }.printIndex(values)
    BinarySearch.firstTrue(0, values.lastIndex) { values[it] > 7 }.printIndex(values)
    values.binarySearch(10).also { println(it) }

    values.sliceBinSearch(8, 14).also { println(it.contentToString()) }

    val p = 25.0
    annualPercentageToMonthly(p).also(::println)
    (((1 + p / 100).pow(1.0 / 12) - 1) * 100).also(::println)
}

fun annualPercentageToMonthly(percent: Double): Double {
    val k = 100 * 1_000_000
    val aim = 1 + percent / 100
    val f = { i: Int -> i.toDouble() / k }

    return BinarySearch.lastFalse(0, k) { it ->
        val x = f(it)
        val s12 = (1..12).fold(1.0) { sum, _ -> sum * (x + 1) }
        s12 > aim
    }.let { f(it) * 100 }
}

class BinarySearch {
    companion object {
        @JvmStatic
        inline fun lastFalse(left: Int, right: Int, func: (Int) -> Boolean): Int {
            if (left > right) return -1
            if (func(left)) return -1

            var l = left
            var r = right
            while (l < r) {
                val m = (l + r + 1) / 2
                if (func(m))
                    r = m - 1
                else
                    l = m
            }
            return l
        }

        @JvmStatic
        inline fun firstTrue(left: Int, right: Int, func: (Int) -> Boolean): Int {
            if (left > right) return -1
            if (!func(right)) return -1

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

        @JvmStatic
        fun <E : Comparable<E>> Array<E>.sliceBinSearch(from: E, to: E): Array<E> {
            val l = firstTrue(0, this.lastIndex) { this[it] >= from }
            val r = lastFalse(0, this.lastIndex) { this[it] > to }
            if (l < 0 || r < 0 || l > r) return this.sliceArray(0..0)
            return this.sliceArray(l..r)
        }

    }

    init {
        println("BinarySearch initialized")
    }
}

fun <T> Int.printIndex(array: Array<T>) {
    if (this in array.indices)
        println("${array::class.qualifiedName}[$this] = ${array[this]}")
    else
        println(("${array::class.qualifiedName}[$this] is undefined. The index ($this) is out of range"))
}

fun <E> Int.printIndex(collection: Collection<E>) {
    if (this in collection.indices)
        println("${collection::class.qualifiedName}[$this] = ${collection.elementAt(this)}")
    else
        println(("${collection::class.qualifiedName}[$this] is undefined. The index ($this) is out of range"))
}