package lesson3

import kotlin.math.min

fun main() {
    val (n, k) = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(2)
    val values = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(n)

    val set = values.take(k + 1).toMutableSet()
    if (set.size < min(n, k - 1)) println("YES")
    else {
        for (i in 0..<n - k - 1) {
            set.remove(values[i])
            if (!set.add(values[i + k + 1])){
                println("YES")
                return
            }
        }
        println("NO")
    }
}