package March5contest

fun main() {
    val (ySize, xSize) = readln().split(" ").map(String::toInt)
    val cells = (1..ySize).flatMap { readln().asSequence() }

    placeBombNew(ySize, xSize, cells).also {
        println("${it.first} ${it.second}")
    }

}

fun placeBombNew(ySize: Int, xSize: Int, cells: List<Char>): Pair<Int, Int> {
    val rows = Array(ySize) { IntArray(xSize) { 0 } }
    for (y in 0..<ySize) {
        var j = -1
        var kills = 0
        for (i in 0..xSize) {
            if (i == xSize) {
                if (j != -1 && kills > 0)
                    rows[y].fill(kills, j, i)
                break
            }
            val cell = cells[i + y * xSize]
            when (cell) {
                'B' -> {
                    kills++
                    if (j == -1) j = i
                }

                '.' -> if (j == -1) j = i
                'W' -> {
                    if (j >= 0) {
                        rows[y].fill(kills, j, i)
                        kills = 0
                        j = -1
                    }
                }
            }
        }
    }

    val columns = Array(xSize) { IntArray(ySize) { 0 } }
    for (x in 0..<xSize) {
        var j = -1
        var kills = 0
        for (i in 0..ySize) {
            if (i == ySize) {
                if (j != -1 && kills > 0)
                    columns[x].fill(kills, j, i)
                break
            }
            val cell = cells[x + i * xSize]
            when (cell) {
                'B' -> {
                    kills++
                    if (j == -1) j = i
                }

                '.' -> if (j == -1) j = i
                'W' -> {
                    if (j >= 0) {
                        columns[x].fill(kills, j, i)
                        kills = 0
                        j = -1
                    }
                }
            }
        }
    }

    val totalMax = cells.asSequence().withIndex()
        .filter { it.value == '.' }
        .map {
            val x = it.index % xSize
            val y = it.index / xSize
            (x to y) to (columns[x][y] + rows[y][x])
        }
        .maxBy { it.second }

    /*
    cells.toMutableList()
        .also { it[totalMax.first.first + totalMax.first.second * xSize] = '*' }
        .chunked(xSize)
        .joinToString("\n") { it.joinToString(separator = "") }
        .also { println("Max kills = ${totalMax.second}:\n$it") }
     */

    return (totalMax.first.second + 1) to (totalMax.first.first + 1)
}


/*
10 20
..W......BB.........
B.W...W...........B.
..W...W...BBB.......
.B....W...........WW
......WWWWWW...B....
....B.........WB....
..W..B..B.....W.....
..W....WW.....W..B..
..WWW.B.W...........
....W.....BBB.......

1 10
.BBWBB.BBW

3 3
WBW
B.B
WBW

3 4
....
....
....

 */