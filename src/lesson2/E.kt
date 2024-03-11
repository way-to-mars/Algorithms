package lesson2


fun main() {
    val n = readln().toInt()
    val berries = (1..n).map {
        val (a, b) = readln().split(" ").map(String::toInt)
        Berry(it, a, b)
    }.sorted()
    // berries.joinToString(separator = "\n").also(::println)
    var maxHeight = 0L
    var pos = 0L
    for (i in berries.indices){
        val up = pos + berries[i].a
        if (up < maxHeight) break
        maxHeight = up
        pos = up - berries[i].b
    }
    println(maxHeight)
    berries.asSequence().map { it.index }.joinToString(separator = " ").also(::println)
}

data class Berry(val index: Int, val a: Int, val b: Int) : Comparable<Berry> {
    private val gain = a - b
    override fun compareTo(other: Berry): Int {
        if (gain > 0 && other.gain <= 0) return -1
        if (gain <= 0 && other.gain > 0) return 1

        if (gain <= 0) return other.a - a
        return b - other.b
    }
    override fun toString() = "($index, $a, $b -> $gain)"
}