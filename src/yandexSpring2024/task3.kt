package yandexSpring2024

fun main() {
    val n = readln().toInt()
    (1..n).map { Word(readln(), it) }.sorted().forEach { println(it.str) }
}

class Word(val str: String, val index: Int) : Comparable<Word> {
    companion object {
        val vowels = charArrayOf('a', 'e', 'i', 'o', 'u')
    }

    override fun compareTo(other: Word): Int {
        val thisVowels = this.str.count { it in vowels }
        val otherVowels = other.str.count { it in vowels }
        if (thisVowels > otherVowels) return -1
        if (thisVowels < otherVowels) return 1

        if (this.str.length < other.str.length) return -1
        if (this.str.length > other.str.length) return 1

        return index.compareTo(other.index)
    }
}