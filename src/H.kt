fun main() {
    val (len, x1, v1, x2, v2) = readln().split(" ").mapNotNull(String::toDoubleOrNull).take(5)

    if (x1 == x2){
        println("YES\n0.0")
    } else {
        (-2..2).flatMap {
            listOf(
                (it * len - (x1 + x2)) / (v1 + v2), (it * len - (x1 - x2)) / (v1 - v2)
            )
        }.filter { it.isFinite() && it > 0 }.minOrNull()?.also { println("YES\n$it") } ?: println("NO")
    }
}


// .filter { it.isFinite() && it > 0 }.minOrNull()?

//948_744_004 861_724_643 848_773_505 584_154_450 730_556_189