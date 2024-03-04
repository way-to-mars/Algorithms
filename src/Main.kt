

fun main() {
    val (p, v) = readIntegers(2)
    val (q, m) = readIntegers(2)
    paintTrees(p = p, q = q, v = v, m = m).also { println(it) }
}

fun readIntegers(count: Int) = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(count)
