package lesson3

fun main(){
    val n = readln().toInt()  //  1 ≤ n ≤ 2 ⋅ 10^5
    readln()  /// ignore it
    var tracks = readln().split(" ").toSortedSet()

    repeat(n - 1){
        readln()  /// ignore it
        val tmp = sortedSetOf<String>()
        readln().split(" ").forEach {
            if (tracks.contains(it)) tmp.add(it)
        }
        tracks = tmp
    }

    println(tracks.size)
    println(tracks.joinToString(separator = " "))
}