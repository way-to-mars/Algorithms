fun main() {
    val (len, x1, v1, x2, v2) = readln().split(" ").mapNotNull(String::toDoubleOrNull).take(5)
    (-1..1).flatMap {
        listOf(
            (it * len - (x1 + x2)) / (v1 + v2), (it * len - (x1 - x2)) / (v1 - v2)
        )
    }.filter { it.isFinite() && it > 0 }.minOrNull()?.also { println("YES\n$it") } ?: println("NO")
}
