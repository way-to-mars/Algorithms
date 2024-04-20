package yandexSpring2024


fun main(){
    val (a, b, c) = readln().split(" ").map(String::toInt)
    if (a == b + c || b == a + c || c == a + b) println("YES")
    else println("NO")
}