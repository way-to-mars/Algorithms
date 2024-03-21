package examples

fun main() {
    allNumbersGenerator(10, 10)
}

fun allNumbersGenerator(size: Int, radix: Int) {
    require(radix <= 16)
    val alphabet = "0123456789ABCDEF"
    val digits = alphabet.asSequence().take(radix)
    val prefix = mutableListOf<Char>()
    var indexer = 0
    fun generateNumbers(m: Int) {
        if (m == 0) {
            if (indexer % 10_000_000 == 0)
                println(prefix.joinToString(separator = ""))
            indexer ++
        }
        else
            digits.forEach { digit ->
                prefix.add(digit)
                generateNumbers(m - 1)
                prefix.removeLast()
            }
    }
    generateNumbers(size)
}