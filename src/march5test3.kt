fun main() {
    val (ySize, xSize) = readln().split(" ").map(String::toInt)
    val cells = (1..ySize).flatMap { readln().asSequence() }

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
    println("${bestY + 1} ${bestX + 1}")
}