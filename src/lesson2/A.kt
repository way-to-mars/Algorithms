package lesson2

fun main() {
    val k = readln().toInt()
    var (minx, miny) = readTwoIntegers()
    var maxx = minx
    var maxy = miny
    repeat(k - 1) {
        val (x, y) = readTwoIntegers()
        if (x < minx) minx = x
        else if (x > maxx) maxx = x
        if (y < miny) miny = y
        else if (y > maxy) maxy = y
    }
    println("$minx $miny $maxx $maxy")
}

fun readTwoIntegers() = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(2)

