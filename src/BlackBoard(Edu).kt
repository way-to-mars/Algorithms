fun main() {
    val iarr: List<Int> = (1..20).map { it * it }

    iarr.toStringLimited(2).printLog()
}