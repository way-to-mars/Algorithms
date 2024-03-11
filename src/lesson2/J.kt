package lesson2

fun main(){
    val (m, n) = readln().split(" ").map(String::toInt)
    val matrix: Array<CharArray> = Array(m) { readln().toCharArray() }

    println(matrix)

    fun firstBlack(): Pair<Int, Int>{
        for (i in matrix.indices)
            for(j in matrix[i].indices)
                if (matrix[i][j] == '#') return i to j
        return -1 to -1
    }
    fun firstFigure(){
        val (y, x) = firstBlack()

    }

}

fun println(matrix: Array<CharArray>){
    matrix.forEach { it.joinToString(separator = "").also(::println) }
}



