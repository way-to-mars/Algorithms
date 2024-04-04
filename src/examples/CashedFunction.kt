package examples

import java.util.concurrent.ConcurrentHashMap

fun main() {
    val p2 = CashedFunction { x: Int ->
        x * x
    }.warmUp(1..10)

}


class CashedFunction<T, R>(private val func: (T) -> R) {
    private val storage = ConcurrentHashMap<T, R>()
    operator fun invoke(t: T): R = storage.getOrPut(t) { func(t) }

    fun warmUp(args: Iterable<T>): CashedFunction<T, R> {
        for (arg in args) storage.getOrPut(arg) { func(arg) }
        return this
    }
}

class CashedFunction2<T1, T2, R>(private val func: (T1, T2) -> R) {
    private val storage = ConcurrentHashMap<Pair<T1, T2>, R>()
    operator fun invoke(a: T1, b: T2): R = storage.getOrPut(a to b) { func(a, b) }
}

