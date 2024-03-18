package lesson3

import java.util.SortedSet


fun main(){
    val wordsDictionary = WordsDictionary()

    wordsDictionary.addWord("abbra")

    println(wordsDictionary.top)
}

class WordsDictionary{
    inner class Node(char: Char) {
        var key: Char = char
        var next: MutableMap<Char, Node>? = null
        var name: String? = null

        fun finalize(word: String){
            name = word
            next = null
        }
    }

    val top = Node('.')
    init{
        top.next = mutableMapOf()
    }

    fun addWord(word: String){
        if (word.isEmpty()) return
        top.next!![word[0]] = createBrunch(word)
    }

    fun createBrunch(word: String): Node{
        var last = Node(word.last()).also { it.name = word }
        for (i in word.lastIndex-1 downTo 0) {
            val node = Node(word[i])
            node.next = mutableMapOf(
                word[i] to last
            )
            last = node
        }
        return last
    }
}

