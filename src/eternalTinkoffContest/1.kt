package eternalTinkoffContest

fun main(){
    val (a, b, c, d) = readln().split(" ").map(String::toInt)
    val extra = d - b
    if (extra <= 0){
        println(a)
    }
    else{
        println(a + c * extra)
    }
}