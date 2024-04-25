package examples

fun main() {
    val arr = Array2D(4, 5) { a, b -> a + 2 * b }

    println(arr)
    println()

    for (i in -1..7) println(arr.getColumn(i).contentToString())
}

class Array2D<T>(val row: Int, val col: Int, init: (Int, Int) -> T) {
    constructor(x: Int, y: Int, init: T) : this(x, y, { _, _ -> init })

    private val data = Array<Any?>(row * col) { init(it % row, it / row) }

    operator fun get(i: Int, j: Int): T {
        if (i !in 0..<row || j !in 0..<col) throw IndexOutOfBoundsException("Can't access to ($i, $j) in Array2D($row, $col)")
        @Suppress("UNCHECKED_CAST")
        return data[i + row * j] as T
    }

    operator fun set(i: Int, j: Int, value: T) {
        if (i !in 0..<row || j !in 0..<col) throw IndexOutOfBoundsException("Can't access to ($i, $j) in Array2D($row, $col)")
        data[i + row * j] = value
    }

    operator fun get(i: Int) = getRow(i)

    fun getRow(i: Int): Array<T>? {
        if (i in 0..<col)
            @Suppress("UNCHECKED_CAST")
            return Array(col) { data[it + i * row] } as Array<T>
        return null
    }

    fun getColumn(j: Int): Array<T>? {
        if (j in 0..<row)
            @Suppress("UNCHECKED_CAST")
            return Array(col) { data[j + it * row] } as Array<T>
        return null
    }

    fun index(linearIndex: Int) = linearIndex % row to linearIndex / row

    fun last(): T {
        @Suppress("UNCHECKED_CAST")
        return data.last() as T
    }

    fun asSequence(): Sequence<T> {
        @Suppress("UNCHECKED_CAST")
        return data.toList().asSequence() as Sequence<T>
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Array2D<*>

        return data.contentEquals(other.data)
    }

    override fun hashCode() = data.contentHashCode()

    override fun toString() = (0..<col)
        .joinToString(separator = "\n") {
            data.drop(it * row).take(row).joinToString(separator = ", ", prefix = "| ", postfix = " |")
        }
}


class IntArray2D(val row: Int, val col: Int) {
    private val data = IntArray(row * col)

    operator fun get(i: Int, j: Int): Int {
        require(i in 0..<row && j in 0..<col) { "Can't access to ($i, $j) in IntArray2D($row, $col)" }
        return data[j + col * i]
    }

    operator fun set(i: Int, j: Int, value: Int) {
        require(i in 0..<row && j in 0..<col) { "Can't access to ($i, $j) in IntArray2D($row, $col)" }
        data[j + col * i] = value
    }

    fun index(linearIndex: Int) = linearIndex / row to linearIndex % row

    fun last()= data.last()

    fun asSequence() = data.toList().asSequence()

    override fun toString() = (0..<row)
        .joinToString(separator = "\n") {
            asSequence().drop(it * col).take(col).joinToString(separator = ", ", prefix = "| ", postfix = " |")
        }
}


/**
 * Префиксные суммы двумерного массива
 */
fun IntArray2D.prefixSums(): IntArray2D {
    val result = IntArray2D(this.row + 1, this.col + 1)

    for (i in 0..<row)
        for (j in 0..<col) {
            result[i + 1, j + 1] = result[i, j + 1] + result[i + 1, j] - result[i, j] + this[i, j]
        }
    return result
}

/**
 * Сумма эементов прямоугольного полузакрытого подмножества соходного множества source
 * this = source.prefixSums()
 */
fun IntArray2D.subSum(fromRow: Int, fromColumn: Int, untilRow: Int, untilColumn: Int): Int {
    require((fromRow >= 0) && (fromRow < untilRow) && (untilRow < row))
    require((fromColumn >= 0) && (fromColumn < untilColumn) && (untilColumn < col))

    val a = this[untilRow, untilColumn]
    val b = this[fromRow, untilColumn]
    val c = this[untilRow, fromColumn]
    val d = this[fromRow, fromColumn]

    return a - b - c + d
}