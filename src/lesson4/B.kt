package lesson4

fun main() {
    fun cells(k: Long): Long{
        var room = k + 1
        var i = 0L
        while (room >= 0){
            i++
            room -= i * (i + 3) / 2
        }
        return i - 1
    }
    val n = readln().toLong()
    println(cells(n))
}
