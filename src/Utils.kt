import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun Any?.printLog(showTime: Boolean = true, showType: Boolean = true, transform: (Any?) -> String = { it.toString() }): Any?{
    if (this == null){
        println("null object")
        return null
    }

    val type = if (showType) "(${this.javaClass.kotlin.qualifiedName}) = " else ""
    val time = if (showTime) "[${LocalDateTime.now()
        .format(DateTimeFormatter.ofPattern("HH:mm:ss.SSSSSS"))}] " else ""
    println("$time$type${transform(this)}")
    return this
}

fun Any?.printLine(): Any? {
    println(this)
    return this
}