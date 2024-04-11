package examples

import printLine
import printLog

fun main(){
    val str1 = "Little cat, little cat, I have no flat"
    val str2 = "cat"
    findSubstringsBruteForce(str1.toList(), str2.toList()).printLog { (it as List<Int>).joinToString(separator = ": ") }

    findSubstringsRabinCarp(str1.toList(), str2.toList())
}

fun <T> findSubstringsBruteForce(where: List<T>, what: List<T>): List<Int> =
    (0..<where.size - what.size).filter { i ->
        !what.indices.any { what[it] != where[it + i]}
    }

fun <T> findSubstringsRabinCarp(where: List<T>, what: List<T>): List<Int>{
    val q: Int = 2147483647

    val subHash = what.fold(0) { sum, i -> sum * 31 + i.hashCode() }.mod(q).printLog()
    var curHash = Sequence{ where.iterator() }.take(what.size).fold(0) { sum, i -> sum * 31 + i.hashCode() }.mod(q).printLog()

    println("-------")

    var index = 0
    val count = where.size - what.size
    while (true){

        index ++
        if (index >= count) break
        // calc new hash
        curHash = Sequence{ where.iterator() }
            .drop(index).take(what.size)
            .fold(0) { sum, i -> sum * 31 + i.hashCode() }.mod(q).printLog()
    }


    return emptyList()
}