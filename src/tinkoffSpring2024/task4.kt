package tinkoffSpring2024

fun main() {
    val firstLine = readln().split(" ")
    val n = firstLine[0].toInt()

    val matrix = Matrix(n)
    val range = 0..n - 1

    repeat(n) { row ->
        val line = readln().split(" ")
        range.forEach { col ->
            matrix[row, col] = line[col].toLong()
        }
    }

    if (firstLine[1] == "R") {
        println(matrix.countRight())
        matrix.printRight()
    } else {
        println(matrix.countLeft())
        matrix.printLeft()
    }
}


class Matrix(val n: Int) {
    private val data = LongArray(n * n) { 0L }

    operator fun set(row: Int, column: Int, value: Long) {
        data[column + n * row] = value
    }

    operator fun get(row: Int, column: Int): Long {
        return data[column + n * row]
    }

    fun invertVertical(verbose: Boolean = false): Int {
        var count = 0
        for (row in 0..n / 2 - 1) {
            val row2 = n - row - 1
            for (col in 0..n - 1) {
                val a = get(row, col)
                val b = get(row2, col)
                if (a != b) {
                    set(row, col, b)
                    set(row2, col, a)
                    count++
                    if (verbose) println("$row $col $row2 $col")
                }
            }
        }
        return count
    }

    fun invertHorizontal(verbose: Boolean = false): Int {
        var count = 0
        for (col in 0..n / 2 - 1) {
            val col2 = n - col - 1
            for (row in 0..n - 1) {
                val a = get(row, col)
                val b = get(row, col2)
                if (a != b) {
                    set(row, col, b)
                    set(row, col2, a)
                    count++
                    if (verbose) println("$row $col $row $col2")
                }
            }
        }
        return count
    }

    fun invertDiagonal(verbose: Boolean = false): Int {
        var count = 0
        for (row in 0..n - 2)
            for (col in 0..n - row - 2) {
                val a = get(row, col)
                val row2 = n - col - 1
                val col2 = n - row - 1
                val b = get(row2, col2)
                if (a != b) {
                    set(row, col, b)
                    set(row2, col2, a)
                    count++
                    if (verbose) println("$row $col $row $col2")
                }
            }
        return count
    }

    fun countRight(): Int {
        val count = invertHorizontal() + invertDiagonal()
        invertDiagonal()
        invertHorizontal()
        return count
    }

    fun countLeft(): Int {
        val count = invertVertical() + invertDiagonal()
        invertDiagonal()
        invertVertical()
        return count
    }

    fun printRight() {
        invertHorizontal(true) + invertDiagonal(true)
    }

    fun printLeft() {
        invertVertical(true) + invertDiagonal(true)
    }

    override fun toString() = (0..n - 1)
        .joinToString(separator = "\n") {
            data.drop(it * n).take(n).joinToString(separator = ", ", prefix = "| ", postfix = " |")
        }
}