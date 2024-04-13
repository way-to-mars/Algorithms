@file:Suppress("ReplaceRangeToWithRangeUntil")

package tinkoffSpring2024

import printLine
import printLog

fun main() {
    val n = readln().toInt()  // 2..100
    var start = Point(-1, -1)
    var finish = Point(-1, -1)
    val horses: MutableSet<Point> = mutableSetOf()
    val kings: MutableSet<Point> = mutableSetOf()
    (0..<n).forEach { i ->
        val line = readln()
        line.indices.forEach { j ->
            val ch = line[j]
            when (ch) {
                'S' -> start.assign(i, j)
                'F' -> finish.assign(i, j)
                'K' -> horses += Point(i, j)
                'G' -> kings += Point(i, j)
            }
        }
    }


    start.printLog()
    finish.printLog()
    horses.printLog()
    kings.printLog()

    val chess = Desk(n)
    chess[finish] = 0
    chess.printLine()

    Point(3, 3).horseMoves(14).joinToString().printLog()

    var step = 0
    while (true) {
        var count = 0
        chess.filter(step).forEach {
            it.horseMoves(n).forEach { horsePoint ->
                if (!kings.contains(horsePoint)){
                    if (horsePoint == start) println("Horse is here!!! step = ${step + 1}")
                    if (chess[horsePoint] == -1)
                        chess[horsePoint] = step + 1

                    }
            }
            println()
            chess.printLine()
            it.kingMoves(n).forEach { kingPoint ->
                if (!horses.contains(kingPoint))
                    if (chess[kingPoint] == -1)
                        chess[kingPoint] = step + 1
            }
            println()
            chess.printLine()
            count++
        }
        step++
        if (count == 0) break
    }



}


data class ChessCell(var state: State = State.untouched, var distance: Int = -1) {
    companion object {
        val unavailable = ChessCell(State.old, -1)
        val unchecked = ChessCell(State.untouched, -1)
    }

    enum class State { untouched, new, old }
}

data class Point(var row: Int, var column: Int) {
    fun assign(r: Int, c: Int) {
        row = r
        column = c
    }

    fun isEqual(r: Int, c: Int) = r == row && c == column

    fun kingMoves(n: Int) = sequence<Point> {
        val allowed = 0..(n - 1)
        for (i in -1..1)
            for (j in -1..1) {
                if (i == 0 && j == 0) continue
                val r = row + i
                val c = column + j
                if (r in allowed && c in allowed)
                    yield(Point(r, c))
            }
    }

    fun horseMoves(n: Int) = sequence<Point> {
        val allowed = 0..(n - 1)
        listOf(1 to 2, 1 to -2, 2 to 1, 2 to -1, -1 to 2, -1 to -2, -2 to 1, -2 to -1).forEach {
            val r = row + it.first
            val c = column + it.second
            if (r in allowed && c in allowed)
                yield(Point(r, c))
        }
    }
}

class Desk(val n: Int) {
    private val data = Array(n * n) { -1 }

    operator fun get(point: Point) = get(point.row, point.column)

    operator fun get(row: Int, col: Int): Int {
        if (row !in 0..<n || col !in 0..<n) return -1
        return data[col + n * row]
    }

    operator fun set(point: Point, value: Int) = set(point.row, point.column, value)

    operator fun set(row: Int, col: Int, value: Int) {
        if (row !in 0..<n || col !in 0..<n) return Unit
        data[col + n * row] = value
    }

    fun filter(step: Int) = sequence<Point> {
        data.indices.asSequence().filter { data[it] == step }.forEach {
            yield(Point(it % n, it / n))
        }
    }


    override fun toString() = (0..<n)
        .joinToString(separator = "\n") {
            data.drop(it * n).take(n).joinToString(separator = ", ", prefix = "| ", postfix = " |")
        }
}

/*
(3)
3
S..
F..
...

(-1)
2
SF
..

(3)
4
S..K
..G.
.GK.
.K.F

 */