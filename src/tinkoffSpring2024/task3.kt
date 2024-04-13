package tinkoffSpring2024

fun main() {
    val n = readln().toInt()  // 1.. 10^5
    val dirs = (1..n).map{ Sub(readln()) }.sortedDescending()

    dirs.forEach(::println)
}

class Sub(input: String) : Comparable<Sub> {
    private val data = input.split("/")
    override fun compareTo(other: Sub): Int {
        fun minOf(a: Int, b: Int) = if (a < b) a else b
        val c = minOf(data.size, other.data.size)
        for (i in 0..c - 1) {
            val cmp = -data[i].compareTo(other.data[i])
            if (cmp != 0) return cmp
        }
        return other.data.size.compareTo(data.size)
    }

    override fun toString(): String {
        val sb = StringBuilder()
        repeat(data.size - 1){ sb.append("  ") }
        sb.append(data.last())
        return sb.toString()
    }
}