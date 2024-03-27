package lesson4

import kotlin.math.sqrt

fun main(){
    val n = readln().toLong()  // 1 ≤ n ≤ 10^18

    val x = (sqrt(8 * n.toDouble() + 1) - 1) / 2
    val lx = x.toLong()
    val lxpp = lx + 1
    val dgn = if (n > lx * lxpp / 2) lxpp else lx    // diagonal number

    val ascending = dgn % 2 == 0L
    val i = n - dgn * (dgn - 1) / 2   // index inside the diagonal
    val row = if (ascending) dgn - i + 1 else i
    val column = if (ascending) i else dgn - i + 1

    println(byTable(row, column))
}

fun byTable(row: Long, column: Long): String{  // (r, c) = 1, 2, 3 ... infinity
    return "$row/$column"
}

/**
 * (1,1) -- 1 -- 1 = 1 * 2 / 2
 * (2,1) - (1,2) -- 2 -- 3 = 2 * 3 / 2
 * (1,3) - (2,2) - (3,1) -- 3 -- 6 = 3 * 4 / 2
 * (4,1) - (3,2) - (2,3) - (4,1) -- 4 -- 10 = 4 * 5 / 2
 */
