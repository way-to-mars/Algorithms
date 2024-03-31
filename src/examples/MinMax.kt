package examples

import kotlin.random.Random
import kotlin.system.measureTimeMillis


fun main() {
    val rnd = Random
    measureTimeMillis {
        val arr = (1..10_000_000).map {
            val size = rnd.nextInt(1, 16)
            val values = (1..size).map { rnd.nextInt(100) }
            if (values.fastMinOf() != values.min())
                println("min")
            if (values.fastMaxOf() != values.max())
                println("max")
        }
        arr.last().also { println(it) }
    }.also { println("Time = $it milliseconds") }
}


fun <T : Comparable<T>> List<T>.fastMinOf(): T? {
    fun minOf2(a: T, b: T) = if (a < b) a else b
    fun minOf3(a: T, b: T, c: T) = if (a < b) {
        if (a < c) a else c
    } else {
        if (b < c) b else c
    }
    if (this.isEmpty()) return null
    return when (this.size) {
        1 -> this[0]
        2 -> minOf2(this[0], this[1])
        3 -> minOf3(this[0], this[1], this[2])
        4 -> minOf2(minOf2(this[0], this[1]), minOf2(this[2], this[3]))
        5 -> minOf2(minOf2(this[0], this[1]), minOf3(this[2], this[3], this[4]))
        6 -> minOf3(minOf2(this[0], this[1]), minOf2(this[2], this[3]), minOf2(this[4], this[5]))
        7 -> minOf3(minOf3(this[0], this[1], this[2]), minOf2(this[3], this[4]), minOf2(this[5], this[6]))
        8 -> minOf2(
            minOf2(minOf2(this[0], this[1]), minOf2(this[2], this[3])),
            minOf2(minOf2(this[4], this[5]), minOf2(this[6], this[7]))
        )
        else -> this.min()
    }
}

fun <T : Comparable<T>> List<T>.fastMaxOf(): T? {
    fun maxOf2(a: T, b: T) = if (a > b) a else b
    fun maxOf3(a: T, b: T, c: T) = if (a > b) {
        if (a > c) a else c
    } else {
        if (b > c) b else c
    }
    if (this.isEmpty()) return null
    return when (this.size) {
        1 -> this[0]
        2 -> maxOf2(this[0], this[1])
        3 -> maxOf3(this[0], this[1], this[2])
        4 -> maxOf2(maxOf2(this[0], this[1]), maxOf2(this[2], this[3]))
        5 -> maxOf2(maxOf2(this[0], this[1]), maxOf3(this[2], this[3], this[4]))
        6 -> maxOf3(maxOf2(this[0], this[1]), maxOf2(this[2], this[3]), maxOf2(this[4], this[5]))
        7 -> maxOf3(maxOf3(this[0], this[1], this[2]), maxOf2(this[3], this[4]), maxOf2(this[5], this[6]))
        8 -> maxOf2(
            maxOf2(maxOf2(this[0], this[1]), maxOf2(this[2], this[3])),
            maxOf2(maxOf2(this[4], this[5]), maxOf2(this[6], this[7]))
        )
        else -> this.max()
    }
}