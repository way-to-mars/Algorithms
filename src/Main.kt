fun main() {
    val n = readln().toIntOrNull() ?: 0
    println((1..n).sumOf { readln().toLong().toTaps() })
}
