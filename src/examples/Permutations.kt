package examples

fun main() {
    allNumbersGenerator(size = 3, radix = 2)

    val list = listOf('A', 'B', 'B', 'A')
    val iter = list.combinations { it.isAnagram() }

    iter.forEach(::println)

}

fun allNumbersGenerator(size: Int, radix: Int) {
    require(radix <= 16)
    val alphabet = "0123456789ABCDEF".asSequence()
    val digits = alphabet.take(radix)
    val prefix = mutableListOf<Char>()
    var indexer = 0
    fun generateNumbers(m: Int) {
        if (m == 0) {
            println(prefix.joinToString(separator = ""))
            indexer++
        } else
            digits.forEach { digit ->
                prefix.add(digit)
                generateNumbers(m - 1)
                prefix.removeLast()
            }
    }
    generateNumbers(size)
}

fun <T> List<T>.combinations(predicate: (List<T>) -> Boolean): List<List<T>> {
    val asSet: Set<Pair<Int, T>> = this.withIndex().map { it.index to it.value }.toSet()
    val prefix = mutableListOf<T>()
    val result = mutableListOf<List<T>>()
    fun generateCombinations(subSet: Set<Pair<Int, T>>) {
        if (subSet.isEmpty() && predicate(prefix)) {
            result.add(prefix.toList())
        } else {
            subSet.forEach { a ->
                prefix.add(a.second)
                generateCombinations(subSet - a)
                prefix.removeLast()
            }
        }
    }
    generateCombinations(asSet)
    return result
}

fun <T : Comparable<T>> List<T>.isAnagram(): Boolean {
    val e = this.size - 1
    for (i in 0..<this.size / 2) {
        if (this[i] != this[e - i]) return false
    }
    return true
}