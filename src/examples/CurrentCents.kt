package examples

import kotlin.math.abs

fun main() {
    val a = CurrentCents(30, 50)
    val b = CurrentCents(12, 22)
    val c = a + b + b + b
    val d = a - b
    val e = b - a
    println("$c $d $e")
}

@JvmInline
value class CurrentCents private constructor(private val totalCents: Long = 0) : Comparable<CurrentCents> {
    fun integerPart() = totalCents / 100
    fun fractionalPart() = totalCents % 100

    constructor(integer: Int, fractional: Int) : this(integer.toLong() * 100 + fractional){
        require((fractional >= 0) && (fractional < 100)) { "Fractional part of current must be in 0..99" }
    }

    override fun compareTo(other: CurrentCents): Int {
        return when {
            totalCents < other.totalCents -> -1
            totalCents > other.totalCents -> 1
            else -> 0
        }
    }

    fun toDouble() = totalCents.toDouble() / 100
    fun toFloat() = totalCents.toFloat() / 100
    operator fun plus(other: CurrentCents) = CurrentCents(totalCents + other.totalCents)
    operator fun minus(other: CurrentCents) = CurrentCents(totalCents - other.totalCents)

    override fun toString(): String {
        return "${totalCents / 100}.${(abs(totalCents) / 10) % 10}${abs(totalCents) % 10}"
    }
}