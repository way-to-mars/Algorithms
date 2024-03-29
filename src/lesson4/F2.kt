package lesson4

import java.util.*

typealias myMap = HashMap<Int, Pair<Int, Int>>

fun main() {
    val (w, h, n) = readln().split(" ").map(String::toInt)
    val slabsBroken = ArrayList<Pair<Int, Int>>(n)
    repeat(n) {
        val (y, x) = readln().split(" ").map { it.toInt() }
        slabsBroken.add(x to y)
    }

    val columns = slabsBroken.fold(HashMap<Int, Pair<Int, Int>>()) { hmap, pair ->
        if (hmap.containsKey(pair.second)) {
            if (hmap[pair.second]!!.first > pair.first)
                hmap[pair.second] = pair.first to hmap[pair.second]!!.second
            if (hmap[pair.second]!!.second < pair.first)
                hmap[pair.second] = hmap[pair.second]!!.first to pair.first
        } else {
            hmap[pair.second] = Pair(pair.first, pair.first)
        }
        hmap
    }

    val rows = slabsBroken.fold(HashMap<Int, Pair<Int, Int>>()) { hmap, pair ->
        if (hmap.containsKey(pair.first)) {
            if (hmap[pair.first]!!.first > pair.second)
                hmap[pair.first] = pair.second to hmap[pair.first]!!.second
            if (hmap[pair.first]!!.second < pair.second)
                hmap[pair.first] = hmap[pair.first]!!.first to pair.second
        } else {
            hmap[pair.first] = Pair(pair.second, pair.second)
        }
        hmap
    }

    println(binarySearch(w, h, columns, rows))
}

data class Atom(val key: Int, val min: Int, val max: Int) {
    constructor(elem: MutableMap.MutableEntry<Int, Pair<Int, Int>>) : this(
        elem.key,
        elem.value.first,
        elem.value.second
    )
}

fun check(n: Int, k: Int, col: myMap, row: myMap): Boolean {
    val window = ArrayDeque<Atom>()

    val itr = col.iterator()
    val e = col.keys.sorted()
    window.add(Atom(e[0], col[e[0]]!!.first, col[e[0]]!!.second))
    for (i in 0..<k) { // repeat(k)
        if (e[i] < window.first.key + k) {
            window.add(Atom(e[i], col[e[i]]!!.first, col[e[i]]!!.second))
            itr.next()
            if (itr.hasNext()) break
        } else
            break
    }

    var minRow = col.entries.asSequence().drop(window.size - 1).minOf { it.value.first }
    var maxRow = col.entries.asSequence().drop(window.size - 1).maxOf { it.value.second }

    println(" ${col.entries.drop(window.size - 1).size} k=$k ws=${window.size} cs=${col.size}")

    if (window.size >= n) return true
    if (maxRow - minRow < k) return true

   // for (item in col.entries.drop(window.size - 1)) {
    for(key in e.asSequence().drop(window.size - 1)){
        val item = Atom(key, col[key]!!.first, col[key]!!.second)
        for (i in 0..<window.size) {
            if (window.first.key <= item.key - k) {
                if (window.first.max > maxRow)
                    maxRow = window.first.max
                if (window.first.min < minRow)
                    minRow = window.first.min
                window.removeFirst()
            } else {
                break
            }
        }

        window.add(item)

        if (maxRow == item.max) {
            val l = row[maxRow]!!.first
            val r = row[maxRow]!!.second
            if (item.key == r && l > r - k) {
                val itr2 = row.entries.reversed().iterator()
                while (itr2.next().key != maxRow) {
                    Unit
                }
                while (true) {
                    val kv = itr2.next()
                    val ll = kv.value.first
                    val rr = kv.value.second
                    if (rr > r || ll <= r - k) {
                        maxRow = kv.key
                        break
                    }
                }
            }
        }

        if (minRow == item.min) {
            val l = row[minRow]!!.first
            val r = row[minRow]!!.second
            if (item.key == r && l > r - k) {

                val itr3 = row.entries.iterator()
                while (itr3.next().key != minRow) {
                    Unit
                }
                while (true) {
                    val kv = itr3.next()
                    val ll = kv.value.first
                    val rr = kv.value.second
                    if (rr > r || ll <= r - k) {
                        minRow = kv.key
                        break
                    }
                }
            }
        }

        if (maxRow - minRow < k)
            return true
    }
    return false
}

fun binarySearch(w: Int, h: Int, columns: myMap, rows: myMap): Int {
    var l = 1
    var r = minOf(w, h)
    while (l < r) {
        val m = (l + r) / 2
        if (check(h, m, columns, rows))
            r = m
        else
            l = m + 1
    }
    return l
}