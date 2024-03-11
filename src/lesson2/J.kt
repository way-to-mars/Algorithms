package lesson2

fun main() {
    val picture = Picture()

    if (picture.blackSquares <= 1) {
        println("NO")
        return
    }

    val dimOfA = picture.paintRectangle('a')
    val dimOfB = picture.paintRectangle('b')

    if (picture.blackSquares > 0) {
        println("NO")
        return
    }

    if (dimOfB == 0 to 0) picture.cut(dimOfA, 'a', 'b')
    println("YES")
    println(picture)
}

class Picture {
    private val m: Int
    private val n: Int
    private val matrix: Array<CharArray>

    init {
        readln().split(" ").map(String::toInt).also {
            m = it[0]
            n = it[1]
        }
        matrix = Array(m) { readln().toCharArray() }
    }

    val blackSquares: Int
        get() = matrix.sumOf { it.count { ch -> ch == '#' } }

    private fun firstChar(char: Char): Pair<Int, Int> {
        for (i in matrix.indices) for (j in matrix[i].indices) if (matrix[i][j] == char) return i to j
        return -1 to -1
    }

    fun paintRectangle(char: Char): Pair<Int, Int> {  // returns area of painted area
        val (x, y) = firstChar('#')
        if (x < 0 || y < 0) return 0 to 0

        var width = 0
        for (i in x..<m) {
            if (matrix[i][y] == '#') {
                matrix[i][y] = char
                width++
            } else break
        }
        var height = 1
        for (j in y + 1..<n) {
            var hasLine = true
            for (i in 0..<width) if (matrix[x + i][j] != '#') {
                hasLine = false
                break
            }
            if (hasLine) {
                for (i in 0..<width) matrix[x + i][j] = char
                height++
            } else break
        }
        return width to height
    }

    fun cut(dimm: Pair<Int, Int>, ch1: Char, ch2: Char) {
        val (x, y) = firstChar(ch1)
        val (width, height) = dimm

        if (width == 1) matrix[x][y] = ch2
        else {
            for (i in 0..<height) matrix[x][y + i] = ch2
        }

    }

    override fun toString() = matrix.joinToString(separator = "\n") { it.joinToString(separator = "") }
}

/*
2 1
#
.

2 2
..
##

1 3
###

1 5
####.

4 3
.##
.##
...
###

4 3
##.
##.
.##
...


 */