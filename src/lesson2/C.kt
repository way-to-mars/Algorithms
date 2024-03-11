package lesson2

fun main(){
    val n = readln().toInt()
    val lengths = readln().split(" ").map(String::toInt)

    val sum = lengths.sum()
    val max = lengths.max()

    val l1 = 2 * max - sum
    val L = sum

    if (l1 > 0) println(l1)
    else println(L)
}