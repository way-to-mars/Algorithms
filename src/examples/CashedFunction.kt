package examples

class CashedFunction<T, R>(private val func: (T) -> R) {
    private val storage = mutableMapOf<T, R>()

    fun invoke(t: T): R {
        if (!storage.containsKey(t)) storage[t] = func(t)
        return storage[t]!!
    }
}