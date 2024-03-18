package lesson3

fun main(){
    val n = readln().toInt()  //  1 ≤ n ≤ 2 ⋅ 10^5
    readln()
    var tracks = readln().split(" ")

    repeat(n - 1){
        readln()
        tracks = readln().split(" ").filter { tracks.contains(it) }
    }

    println(tracks.size)
    println(tracks.sorted().joinToString(separator = " "))
}