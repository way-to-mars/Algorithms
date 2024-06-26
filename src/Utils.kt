import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun <T> T.printLog(
    showTime: Boolean = true,
    showType: Boolean = true,
    transform: (T) -> String = { it.toString() }
): T {
    if (this == null) {
        println("null object")
        return this
    }
    val type = if (showType) "(${this.javaClass.kotlin.qualifiedName}) " else ""
    val time = if (showTime) "[${
        LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS"))
    }] " else ""
    println("$time$type${transform(this)}")
    return this
}

fun Any?.printLine(): Any? {
    println(this)
    return this
}

// Быстрое возведение в степень (p >= 0)
fun Int.fastPow(p: Int): Int {
    if (p == 0) return 1
    return if (p % 2 == 0)
        (this * this).fastPow(p / 2)
    else
        this * (this * this).fastPow((p - 1) / 2)
}

fun <T : Comparable<T>> List<T>.fastMinOf(): T? {
    fun minOf2(a: T, b: T) = if (a < b) a else b
    fun minOf3(a: T, b: T, c: T) = if (a < b && a < c) a else if (b < c) b else c

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
    fun maxOf3(a: T, b: T, c: T) = if (a > b && a > c) a else if (b > c) b else c

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


/**
 * Преобразует последовательность элементов в строку.
 * Если количество элементов превышает limit, то возвращает строку по шаблону:
 * "[T1, T2, ... Tn-1, Tn]: size=n",
 * где количество отображаемых элементов соответствует параметру limit
 */
fun <T> Iterable<T>.toStringLimited(limit: Int): String {
    val size = this.fold(0) { acc, _ -> acc + 1 }
    return when {
        size <= limit -> this.joinToString(separator = ", ", prefix = "[", postfix = "]")
        else -> {
            if (limit <= 0) return "[...]: size=$size"
            val n = (limit + 1) / 2
            val m = limit / 2
            this.take(n).joinToString(separator = ", ", prefix = "[", postfix = ", ... ") +
                    this.drop(size - m).joinToString(separator = ", ", postfix = "]: size=${size}")
        }
    }
}

fun IntArray.prefixSums(): IntArray {
    val result = IntArray(this.size + 1).also { it[0] = 0 }
    for (i in this.indices)
        result[i + 1] = result[i] + this[i]
    return result
}

fun LongArray.prefixSums(): LongArray {
    val result = LongArray(this.size + 1).also { it[0] = 0 }
    for (i in this.indices)
        result[i + 1] = result[i] + this[i]
    return result
}