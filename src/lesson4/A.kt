package lesson4

fun main(){
    val n = readln().toInt()
    val taskA = taskA(n)
    val k = readln().toInt()
    (1..k).map {
        val (l, r) = readln().split(" ").map(String::toInt)
        taskA.calc(l, r)
    }.joinToString(separator = " ").also(::println)
}

class taskA(n: Int){
    val values = readln().split(" ").map(String::toInt).sorted().toIntArray()

    fun calc(left: Int, right: Int): Int{
        return right(right) - left(left)
    }

    private fun left(value: Int): Int{
        if (values.isEmpty() || value < values.first()) return 0


        return 0
    }

    private fun right(value: Int): Int{
        if (values.isEmpty() || value > values.last()) return values.size

        return values.lastIndex + 1
    }


}