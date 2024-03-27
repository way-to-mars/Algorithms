package lesson3

fun main() {
    val (n, k) = readln().split(" ").map(String::toInt)

    val workGroup = WorkGroup(n, k)
    while(!workGroup.finished)
        workGroup.step()

    workGroup.units.asSequence().drop(1).map { it.slotsTaken }.joinToString(separator = " ").also(::println)
}

class WorkGroup(val n: Int, val k: Int) {
    val units = (1..n).map { Unit(k, it) }
    val finished: Boolean
        get() = !units.any { it.countLoaded() < k }
    fun step() {
        val frequencyMap = IntArray(k) { i ->
            units.count { it.parts[i] }
        }
        (1..<n).forEach {
            val partIndex = units[it].findNextPart(frequencyMap)
            /**
             * устройство делает запрос выбранной части обновления у одного из устройств, на котором такая часть
             * обновления уже скачана. Если таких устройств несколько — выбирается устройство, на котором скачано
             * наименьшее количество частей обновления. Если и таких устройств оказалось несколько —
             * выбирается устройство с минимальным номером.
             */
            if (partIndex != -1) {
                val sourceIndex = units
                    .withIndex()
                    .filter { u -> u.value.parts[partIndex] }
                    .minBy { u -> u.value.countLoaded() }
                    .index
                units[sourceIndex].ask(asker = units[it], partIndex = partIndex)
                units[it].rounds++
            }
        }
        (0..<n).mapNotNull {
            units[it].answer()
        }.forEach {
            it.first.transmit(it.second.first, it.second.second)
        }
    }
}

class Unit(k: Int, val number: Int) {
    val parts = BooleanArray(k) { number == 1 }
    var rounds = 0
    var slotsTaken = -1
    var done = false
    val ratings = mutableMapOf<Unit, Int>()         // Unit to uploads count
    val requests = mutableListOf<Pair<Unit, Int>>()  // Unit to partIndex

    fun countLoaded() = parts.count { it }

    /**
    Перед каждым таймслотом для каждой части обновления определяется, на скольких устройствах сети скачана эта часть.
    Каждое устройство выбирает отсутствующую на нем часть обновления, которая встречается в сети реже всего.
    Если таких частей несколько, то выбирается отсутствующая на устройстве часть обновления с наименьшим номером.
     */
    fun findNextPart(freqMap: IntArray) = freqMap.withIndex()
        .filter { !this.parts[it.index] }
        .minByOrNull { it.value }
        ?.index ?: -1

    fun ask(asker: Unit, partIndex: Int) {
        requests.add(asker to partIndex)
    }

    fun answer(): Pair<Unit, Pair<Unit, Int>>? {
        if (requests.isEmpty()) return null

        /**
         *  устройство выбирает, чей запрос удовлетворить. Устройство A удовлетворяет тот запрос, который поступил от
         *  наиболее ценного для A устройства. Ценность устройства B для устройства A определяется как количество
         *  частей обновления, ранее полученных устройством A от устройства B. Если на устройство A пришло несколько
         *  запросов от одинаково ценных устройств, то удовлетворяется запрос того устройства, на котором меньше всего
         *  скачанных частей обновления. Если и таких запросов несколько, то среди них выбирается устройство с
         *  наименьшим номером.
         */

        val bestRating = requests.maxOfOrNull { ratings.getOrDefault(it.first, 0) } ?: 0
        val bestUnits = requests.filter { ratings.getOrDefault(it.first, 0) == bestRating }
        val minLoaded = bestUnits.minBy { it.first.countLoaded() }.first.countLoaded()
        val choice = bestUnits.filter { it.first.countLoaded() == minLoaded }.minBy { it.first.number }

        requests.clear()  // after all
        return choice.first to (this to choice.second)
    }

    fun transmit(unit: Unit, partIndex: Int) {
        ratings[unit] = ratings.getOrDefault(unit, 0) + 1
        parts[partIndex] = true

        if (!parts.any{ it == false }){
            done = true
            slotsTaken = rounds
        }
    }

    override fun toString() = parts.map { if (it) 1 else 0 }
        .joinToString(separator = "", prefix = "#$number[", postfix = "]")

}