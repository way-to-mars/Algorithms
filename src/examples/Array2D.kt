package examples

fun main() {
    val arr = Array2D(4, 5) { a, b -> a + 2 * b }

    println(arr)
    println()

    for (i in -1..7) println(arr.column(i).contentToString())
}

class Array2D<T>(val x: Int, val y: Int, init: (Int, Int) -> T) {
    constructor(x: Int, y: Int, init: T) : this(x, y, { _, _ -> init })
    private val data = Array<Any?>(x * y) { init(it % x, it / x) }

    operator fun get(i: Int, j: Int): T {
        if (i !in 0..<y || j !in 0..<x) throw IndexOutOfBoundsException("Can't access to ($i, $j) in Array2D($x, $y)")
        @Suppress("UNCHECKED_CAST")
        return data[i + y * j] as T
    }
    
    operator fun get(i: Int) = row(i)

    fun row(i: Int): Array<T>? {
        if (i in 0..<y)
            @Suppress("UNCHECKED_CAST")
            return Array(y){ data[it + i * x] } as Array<T>
        return null
    }

    fun column(j: Int): Array<T>? {
        if (j in 0..<x)
            @Suppress("UNCHECKED_CAST")
            return Array(y){ data[j + it * x] } as Array<T>
        return null
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Array2D<*>

        return data.contentEquals(other.data)
    }

    override fun hashCode() = data.contentHashCode()

    override fun toString() = (0..<y)
        .joinToString(separator = "\n") {
            data.drop(it * x).take(x).joinToString(separator = ", ", prefix = "| ", postfix = " |")
        }
}