import examples.IntArray2D
import kotlin.jvm.Throws

fun main() {
    val arr = arrayOf(5, 4, 7, 2, 2, -1, 8).toIntArray()
    arr.prefixSums().printLog { it.contentToString() }

    val arr2d = IntArray2D(6, 6)
    for (i in 0..34) {
        val (x, y) = arr2d.index(i)
        arr2d[x, y] = i + 1
    }
    arr2d[5, 5] = -36
    arr2d.printLine()
    println()
    arr2d.prefixSums().subSum(0,0,6,6).printLine()
    arr2d.asSequence().sum().printLine()
}

fun IntArray2D.prefixSums(): IntArray2D {
    val result = IntArray2D(this.row + 1, this.col + 1)

    for (i in 0..<row)
        for (j in 0..<col) {
            result[i + 1, j + 1] = result[i, j + 1] + result[i + 1, j] - result[i, j] + this[i, j]
        }
    return result
}

fun IntArray2D.subSum(fromRow: Int, fromColumn: Int, untilRow: Int, untilColumn: Int): Int {
    require((fromRow >= 0) && (fromRow < untilRow) && (untilRow < row))
    require((fromColumn >= 0) && (fromColumn < untilColumn) && (untilColumn < col))

    val a = this[untilRow, untilColumn]
    val b = this[fromRow, untilColumn]
    val c = this[untilRow, fromColumn]
    val d = this[fromRow, fromColumn]

    return a - b - c + d
}


