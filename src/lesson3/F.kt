package lesson3

fun main(){
    val dictionary = readln().split(" ").sorted().groupBy { it[0] }

    readln().split(" ").map{ word ->
        val first = word[0]
        if (dictionary.containsKey(first))
            dictionary[first]!!.forEach {
                if (word.startsWith(it)) return@map it
        }
        word
    }.joinToString(separator = " ").also(::println)
}