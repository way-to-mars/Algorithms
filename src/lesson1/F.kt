package lesson1

fun main() {
    val n = readln().toInt()
    val input = readln().split(" ").mapNotNull(String::toIntOrNull).take(n)

    var countOdds = if (input[0] % 2 == 0) 0 else 1
    input.drop(1).map {
        val isOdd = it % 2 != 0
        if (isOdd && countOdds < 2) countOdds++
        if (countOdds < 2) '+' else { if (isOdd) 'x' else '+' }
    }.joinToString(separator = "").also(::println)
}