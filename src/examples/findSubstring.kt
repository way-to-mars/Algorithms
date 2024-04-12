package examples

import fastPow
import printLog

fun main() {
    val str1 = "Little mouse, little mouse, Where is your house? Little cat, little cat, I have no flat. " +
            "I am a poor mouse, I have no house. Little mouse, little mouse," +
            "Come into my house. " +
            "Little cat, little cat, " +
            "I cannot do that, " +
            "You want to eat me!"
    val str2 = "ou"

    findSubstringsBruteForce(str1.toList(), str2.toList()).printLog()
    findSubstringsRabinCarp(str1.toList(), str2.toList()).printLog()

    str1.indexOf(str2).printLog()

    findSubstringsKMP("лилилилил".toList(), "ииииииии".toList()).printLog()
}

/**
 * Прямой поиск подстроки в строке. Сложность O(n*m)
 */
fun <T> findSubstringsBruteForce(where: List<T>, what: List<T>): List<Int> =
    (0..<where.size - what.size).filter { i ->
        !what.indices.any { what[it] != where[it + i] }
    }


/**
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
//            else println("Hash collision")
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

fun <T> findSubstringsKMP(source: List<T>, sub: List<T>): List<Int> {
    if (sub.isEmpty() || source.isEmpty() || source.size < sub.size) return emptyList()
    if (sub.size == 1) return source.indices.filter { source[it] == sub.first() }

    val pi = IntArray(sub.size).also {  // sub.size > 1
        it[0] = 0
        var j = 0
        var i = 1
        while (i < sub.size){
            if (sub[i] != sub[j]){
                if (j == 0){
                    it[i] = 0
                    i++
                }
                else j = it[j - 1]
            }
            else{
                if(sub[i] == sub[j]){
                    it[i] = j + 1
                    i++
                    j++
                }
            }
        }
    }

    pi.printLog { it.contentToString() }

    return emptyList()
}
