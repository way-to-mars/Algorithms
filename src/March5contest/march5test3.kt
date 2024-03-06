package March5contest

import kotlin.random.Random
import kotlin.system.measureTimeMillis

fun main() {
  //  val (ySize, xSize) = readln().split(" ").map(String::toInt)
  //  val cells = (1..ySize).flatMap { readln().asSequence() }

    val xSize = 2_000
    val ySize = 2_000
    val cells = generateField(ySize, xSize)
    val reps = 20

    placeBombOld(ySize, xSize, cells).also {
        println("${it.first} ${it.second}")
    }

    placeBombNew(ySize, xSize, cells).also {
        println("${it.first} ${it.second}")
    }

    measureTimeMillis {
        repeat(reps){
            placeBombOld(ySize, xSize, cells)
            print(".")
        }
    }.also { println("old -> ${it.toDouble()/reps}ms/task") }

    measureTimeMillis {
        repeat(reps){
            placeBombNew(ySize, xSize, cells)
            print(".")
        }
    }.also { println("new -> ${it.toDouble()/reps}ms/task") }
}


fun placeBombOld(ySize: Int, xSize: Int, cells: List<Char>): Pair<Int, Int>{
    var bestX = 0
    var bestY = 0
    var maxKills = -1
    cells.forEachIndexed { index, c ->
        if (c == '.') {
            val x = index % xSize
            val y = index / xSize
            var kills = 0
            for (i in x + 1..<xSize) when (cells[i + xSize * y]) {
                'B' -> kills++
                'W' -> break
            }
            for (i in x - 1 downTo 0) when (cells[i + xSize * y]) {
                'B' -> kills++
                'W' -> break
            }
            for (i in y + 1..<ySize) when (cells[x + xSize * i]) {
                'B' -> kills++
                'W' -> break
            }
            for (i in y - 1 downTo 0) when (cells[x + xSize * i]) {
                'B' -> kills++
                'W' -> break
            }
            if (kills > maxKills) {
                maxKills = kills
                bestX = x
                bestY = y
            }
        }
    }
    return (bestY + 1) to  (bestX + 1)
}


fun generateField(ySize: Int, xSize: Int): List<Char> =
    (1..xSize*ySize).map{
        val r = Random.nextInt(100)
        when(r){
            in 0..65 -> 'W'
            in 75..99 -> 'B'
            else -> '.'
        }
    }
