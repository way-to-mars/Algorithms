package tinkoffSpring2024

import printLog

fun main(){
    val n = readln().toInt()
    val array = readln().split(" ").mapNotNull(String::toIntOrNull).take(n)
    // 7 days

    val window = array.asSequence().take(7).toMutableList().printLog()



}