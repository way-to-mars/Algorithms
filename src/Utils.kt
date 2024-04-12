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
    val type = if (showType) "(${this.javaClass.kotlin.qualifiedName}) = " else ""
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