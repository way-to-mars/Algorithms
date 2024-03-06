fun main() {
    val (n, k, d) = readln().split(" ").map(String::toInt)
    calculateIncome(n, k, d).also(::println)
}


fun calculateIncome(n: Int, k: Int, d: Int): String {
    val x1 = (0..9).map { digit -> 10L * n + digit }.firstOrNull { it % k == 0L }
    if (x1 == null) return "-1"
    val tailZeros = if (d > 1) String(CharArray(d - 1) { '0' }) else ""
    return "${x1}${tailZeros}"
}