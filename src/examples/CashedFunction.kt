package examples

import kotlin.system.measureTimeMillis

fun main() {
    val p2 = { x: Int -> x * x }
}


class CashedFunction<T, R>(private val func: (T) -> R){
    private val storage = hashMapOf<T, R>()

    operator fun invoke(t: T): R {
        if (!storage.containsKey(t)) storage[t] = func(t)
        return storage[t]!!
    }
}