package tinkoffSpring2024

fun main() {
    val n = readln().toInt()
    val array = readln().split(" ").map(String::toInt)

    val window = ArrayDeque<Int>()
    array.asSequence().take(7).forEach { window.addLast(it) }

    var best = if (window.any { it < 4 }) -1 else window.count { it == 5 }

    if (best < 7)
        for (i in 7..n - 1) {
            val e = array[i]
            window.removeFirst()
            window.addLast(e)
            if (e == 5) {
                val cur = if (window.any { it < 4 }) -1 else window.count { it == 5 }
                if (cur > best){
                    best = cur
                    if (best == 7) break
                }
            }
        }

    println(best)
}