package yandexSpring2024

import printLog
import toStringLimited

fun main(){
    val n = readln().toInt()
    val array = IntArray(n)
    array.printLog{ it.asList().toStringLimited(10) }
}