package lesson4

import java.util.*

typealias HMap = HashMap<Int, Pair<Int, Int>>

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

data class Atom(val key: Int, val min: Int, val max: Int)

fun check(n: Int, k: Int, columns: HMap, rows: HMap,
          e: List<Int>,
          resd: List<MutableMap.MutableEntry<Int, Pair<Int, Int>>>,
          resa: List<MutableMap.MutableEntry<Int, Pair<Int, Int>>>): Boolean {
    val deque = ArrayDeque<Atom>()

    val itr = columns.iterator()

    deque.add(Atom(e[0], columns[e[0]]!!.first, columns[e[0]]!!.second))
    for (i in 0..<k) {
        if (e[i] < deque.first.key + k) {
            deque.add(Atom(e[i], columns[e[i]]!!.first, columns[e[i]]!!.second))
            itr.next()
            if (itr.hasNext()) break
        } else
            break
    }

    var minRow = columns.entries.asSequence().drop(deque.size - 1).minOf { it.value.first }
    var maxRow = columns.entries.asSequence().drop(deque.size - 1).maxOf { it.value.second }
    //println(" ${columns.entries.drop(window.size - 1).size} k=$k ws=${window.size} cs=${col.size}")
    if (deque.size >= n) return true
    if (maxRow - minRow < k) return true

    for(key in e.asSequence().drop(deque.size - 1)){
        val item = Atom(key, columns[key]!!.first, columns[key]!!.second)
        for (i in 0..<deque.size) {
            if (deque.first.key <= item.key - k) {
                if (deque.first.max > maxRow)
                    maxRow = deque.first.max
                if (deque.first.min < minRow)
                    minRow = deque.first.min
                deque.removeFirst()
            } else {
                break
            }
        }

        deque.add(item)

        if (maxRow == item.max) {
            val l = rows[maxRow]!!.first
            val r = rows[maxRow]!!.second
            if (item.key == r && l > r - k) {
                val itr2 = resd.iterator()
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
            val l = rows[minRow]!!.first
            val r = rows[minRow]!!.second
            if (item.key == r && l > r - k) {

                val itr3 = resa.iterator()
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

fun binarySearch(w: Int, h: Int, columns: HMap, rows: HMap): Int {
    val e: List<Int> = columns.keys.sorted()
    val resd = rows.entries.sortedByDescending { it.key }
    val resa: List<MutableMap.MutableEntry<Int, Pair<Int, Int>>> = rows.entries.sortedBy { it.key }
    var l = 1
    var r = minOf(w, h)
    while (l < r) {
        val m = (l + r) / 2
        if (check(h, m, columns, rows, e, resd, resa))
            r = m
        else
            l = m + 1
    }
    return l
}