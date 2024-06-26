package lesson1

import kotlin.math.max
import kotlin.math.min

/* A. Покраска деревьев

 Вася и Маша участвуют в субботнике и красят стволы деревьев в белый цвет. Деревья растут вдоль улицы через равные
 промежутки в 1 метр. Одно из деревьев обозначено числом ноль, деревья по одну сторону занумерованы положительными
 числами 1, 2 и т.д., а в другую — отрицательными -1, −2 и т.д.
 Ведро с краской для Васи установили возле дерева P, а для Маши — возле дерева Q.
 Ведра с краской очень тяжелые и Вася с Машей не могут их переставить, поэтому они окунают кисть в ведро и уже с этой
 кистью идут красить дерево. Краска на кисти из ведра Васи засыхает, когда он удаляется от ведра более чем на V метров,
 а из ведра Маши — на M метров. Определите, сколько деревьев может быть покрашено.

Формат ввода
 В первой строке содержится два целых числа P и V
  — номер дерева, у которого стоит ведро Васи и на сколько деревьев он может от него удаляться.
 Во второй строке содержится два целых числа Q и M — аналогичные данные для Маши.
 Все числа целые и по модулю не превосходят 10^8

Формат вывода
 Выведите одно число — количество деревьев, которые могут быть покрашены.

Пример
 Ввод	Вывод
 0 7     25
 12 5

================================
 */

fun main() {
    val (p, v) = readIntegers(2)
    val (q, m) = readIntegers(2)
    paintTrees(p = p, q = q, v = v, m = m).also { println(it) }
}

//fun readIntegers(count: Int) = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(count)

fun paintTrees(p: Int, q: Int, v: Int, m: Int): Int {
    require(v >= 0 && m >= 0) { "Дистанция (V или M) не может быть отрицательным числом" }
    val sumSets = 2 * (v + m + 1)
    val diffSets = run {
        val p1 = p - v
        val p2 = p + v
        val q1 = q - m
        val q2 = q + m
        if (q1 > p2 || q2 < p1) 0 else min(q2, p2) - max(q1, p1) + 1
    }
    return sumSets - diffSets
}
