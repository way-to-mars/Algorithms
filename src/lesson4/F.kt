package lesson4

fun calcPrefixSums(slabs: Array<IntArray>): Array<LongArray> {
    val rows = slabs.size
    val columns = slabs[0].size
    val prefixH = Array(rows) { LongArray(columns + 1) { 0L } }

    for (i in 0..<rows) {
        for (j in 0..<columns) {
            prefixH[i][j + 1] = prefixH[i][j] + slabs[i][j]
        }
    }
    val prefixV = Array(rows + 1) { LongArray(columns + 1) { 0L } }
    for (j in 1..columns) {
        for (i in 0..<rows) {
            prefixV[i + 1][j] = prefixV[i][j] + prefixH[i][j]
        }
    }
    return prefixV
}

fun calcPrefixSumsM(slabs: MutableMap<Pair<Int, Int>, Int>, rows: Int, columns: Int): Array<LongArray> {
    val prefhMap: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    for (i in 0..<rows) {
        for (j in 0..<columns) {
            val p = prefhMap.getOrDefault(i to j, 0) + slabs.getOrDefault(i to j, 0)
            if (p != 0) prefhMap[i to j + 1] = p
        }
    }
    val prefixV = Array(rows + 1) { LongArray(columns + 1) { 0L } }
    for (j in 1..columns) {
        for (i in 0..<rows) {
            prefixV[i + 1][j] = prefixV[i][j] + prefhMap.getOrDefault(i to j, 0)
        }
    }
    return prefixV
}

fun applyWidth(width: Int, prefixes: Array<LongArray>, w: Int, h: Int): Boolean {
    fun getSum(lx: Int, ly: Int, rx: Int, ry: Int) =
        prefixes[rx][ry] - prefixes[lx][ry] - prefixes[rx][ly] + prefixes[lx][ly]

    var x = 0
    while (x <= h - width) {
        var y = 0
        while (y <= w - width) {
            val upperLeft = getSum(0, 0, x, y)
            if (upperLeft != 0L) {
                break
            }
            val upperRight = getSum(0, y + width, x, w)
            val lowerLeft = getSum(x + width, 0, h, y)
            val lowerRight = getSum(x + width, y + width, h, w)
            if (lowerLeft == 0L && upperRight == 0L && lowerRight == 0L) {
                return true
            }
            y++
        }
        x++
        if (getSum(0, 0, x, 1) != 0L) {
            break
        }
    }
    return false
}

fun binarySearch(prefix: Array<LongArray>, w: Int, h: Int): Int {
    var l = 1
    var r = if (w < h) w else h // min(w, h)
    while (l < r) {
        val m = (l + r) / 2
        if (applyWidth(m, prefix, w, h)) {
            r = m
        } else {
            l = m + 1
        }
    }
    return r
}

fun main() {
    val (w, h, n) = readln().split(" ").map(String::toInt)
  //  val slabs = Array(h) { IntArray(w) { 0 } }
    val slabsMap: MutableMap<Pair<Int, Int>, Int> = mutableMapOf()
    repeat(n) {
        val (y, x) = readln().split(" ").map { it.toInt() - 1 }   // convert to zero-based index
      //  slabs[x][y] = 1
        slabsMap[x to y] = 1
    }
  //  val prefixSum = calcPrefixSums(slabs)
    val prefixSumM = calcPrefixSumsM(slabsMap, rows = h, columns = w)
    val result = binarySearch(prefixSumM, w, h)
    println(result)
}