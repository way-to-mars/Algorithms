package examples

import fastMaxOf
import fastMinOf
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


