package examples

import fastPow
import printLog
import kotlin.random.Random

fun main() {
    val alphabet = listOf('a', 'b', 'c', 'd', 'e', 'f')
    val rnd = Random

    val n = rnd.nextInt(15_000, 20_000)
    val m = rnd.nextInt(3, 10)

    val str1 = (1..n).map { alphabet[rnd.nextInt(alphabet.size)] }.joinToString(separator = "").printLog()
    val str2 = (1..m).map { alphabet[rnd.nextInt(alphabet.size)] }.joinToString(separator = "").printLog()

    findSubstringsBruteForce(str1.toList(), str2.toList()).printLog()
    findSubstringsRabinCarp(str1.toList(), str2.toList()).printLog()
    findSubstringsKMP(str1, str2).printLog()

    str1.indexOf(str2).printLog()
}

/**
 * Прямой поиск подстроки в строке. Сложность O(n*m)
 */
fun <T> findSubstringsBruteForce(where: List<T>, what: List<T>): List<Int> =
    (0..<where.size - what.size).filter { i ->
        !what.indices.any { what[it] != where[it + i] }
    }


/**
 * Реализация скользящего хэша кривая !!! Вероятно, виновато переполнение Int
 * Алгоритм Рабина — Карпа — это алгоритм поиска строки, который ищет шаблон, то есть подстроку, в тексте,
 * используя хеширование.
 * Для текста длины n и шаблона длины m его среднее и лучшее время исполнения равно O(n) при правильном выборе
 * хеш-функции, но в худшем случае он имеет эффективность O(nm), что является одной из причин того, почему он
 * не слишком широко используется.
 * Из-за медленного поведения в худшем случае алгоритм Рабина — Карпа хуже алгоритма Кнута — Морриса — Пратта,
 * алгоритма Бойера — Мура и других быстрых алгоритмов поиска строк. Тем не менее, алгоритм Рабина — Карпа можно
 * использовать для поиска набора образцов за линейное время в лучшем случае и квадратичное в труднодостижимом
 * худшем случае; хотя и здесь он проигрывает в худшем случае алгоритму Ахо — Корасик, имеющему линейное время работы.
 */
fun <T> findSubstringsRabinCarp(source: List<T>, sub: List<T>): List<Int> {
    if (sub.isEmpty() || source.isEmpty() || source.size < sub.size) return emptyList()

    val q: Int = 2147483647  // 64-bit = 2^61-1 = 2305843009213693951
    val p: Int = 31 // 2^5 - 1
    val pm = p.fastPow(sub.size - 1)

    val subIndices = sub.indices
    val subHash = sub.fold(0) { sum, it -> sum * p + it.hashCode() }.mod(q)
    var curHash = subIndices.fold(0) { sum, i -> sum * p + source[i].hashCode() }.mod(q)
    val res = mutableListOf<Int>()
    var index = 0
    var rightIndex = sub.size - 1
    while (true) {
        if (curHash == subHash) {
            if (!subIndices.any { i -> source[i + index] != sub[i] }) res.add(index)    // compare substrings
            else println("Hash collision")
        }
        rightIndex++
        if (rightIndex >= source.size) break
        curHash = ((curHash - pm * source[index].hashCode()) * p + source[rightIndex].hashCode()).mod(q)
        index++
//        // check:
//        val curHashStrict = subIndices.fold(0) { sum, i -> sum * p + source[i + index].hashCode() }.mod(q)
//        if(curHashStrict != curHash) "different hashes $curHash != $curHashStrict".printLog()
    }
    return res
}

fun findSubstringsKMP(a: String, b: String): List<Int> {
    return findSubstringsKMP(a, b, a.length, b.length) { str, i -> str[i] }
}

fun <T : Comparable<T>> findSubstringsKMP(a: List<T>, b: List<T>): List<Int> {
    return findSubstringsKMP(a, b, a.size, b.size) { list, i -> list[i] }
}


/**
 * Алгоритм Кнута — Морриса — Пратта (КМП-алгоритм) — эффективный алгоритм, осуществляющий поиск подстроки в строке,
 * используя то, что при возникновении несоответствия само слово содержит достаточно информации, чтобы определить,
 * где может начаться следующее совпадение, минуя лишние проверки. Время работы алгоритма линейно зависит от объёма
 * входных данных
 * @param source исходная строка. Структура типа T, содержащая элементы типа R
 * @param sub искомая подстрока
 * @param sourceLen длина исходной строки
 * @param subLen длина искомой подстроки
 * @param getter функция доступа к элементам строки по индексу
 * @return список индексов всех вхождения подстроки в строку (пустой, если вхождений нет)
 */
inline fun <T : Any, R : Comparable<R>> findSubstringsKMP(
    source: T,
    sub: T,
    sourceLen: Int,
    subLen: Int,
    getter: (T, Int) -> R
): List<Int> {
    val prefix = IntArray(subLen).also {
        var i = 1
        var j = 0
        it[0] = 0
        while (i < subLen) {
            if (getter(sub, i) == getter(sub, j)) {
                it[i] = j + 1
                i++
                j++
            } else {
                if (j == 0) {
                    it[i] = 0
                    i++
                } else {
                    j = it[j - 1]
                }
            }
        }
    }
    val res = mutableListOf<Int>()
    var i = 0
    var j = 0
    while (i < sourceLen) {
        if (getter(source, i) == getter(sub, j)) {
            i++
            j++
            if (j == subLen) {
                res.add(i - subLen)
                j = 0
                i = res.last() + 1
            }
        } else {
            if (j > 0) j = prefix[j - 1]
            else i++
        }
    }
    return res
}