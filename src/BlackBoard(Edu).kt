import kotlin.jvm.Throws

fun main() {
    val arr = arrayOf(5, 4, 7, 2, 2, -1, 8).toIntArray()
    arr.prefixSums().printLog{ it.contentToString() }
}


