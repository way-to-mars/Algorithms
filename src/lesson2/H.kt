package lesson2

fun main() {
    val (n, m) = readIntegers(2)
    // 2 <= n <= 1_000 - количество рас
    // 2 <= m <= 1_000 - количество классов
    val bestPerson = Person(0, 0, 0)
    val persons = Array(n) { i ->  // 1 <= a(ij) <= 10^9
        val row = readIntegers(m).toIntArray()
        val bestHere = row.withIndex().maxBy { it.value }
        if (bestHere.value > bestPerson.strength)
            bestPerson.update(i, bestHere.index, bestHere.value)
        row
    }

    fun maxOutside(i: Int, j: Int): Person {
        val bp = persons
            .map { row ->
                row.withIndex().filter { it.index != j }.maxBy { it.value }
            }.withIndex().filter { it.index != i }.maxBy { it.value.value }
        return Person(bp.index, bp.value.index, bp.value.value)
    }

    // exclude a row
    val prow = maxOutside(bestPerson.i, -1)
    val prow2 = maxOutside(bestPerson.i, prow.j)

    // exclude a column
    val pcol = maxOutside(-1, bestPerson.j)
    val pcol2 = maxOutside(pcol.i, bestPerson.j)

    if (pcol2 > prow2) println("${bestPerson.i + 1} ${prow.j + 1}")
    else println("${pcol.i + 1} ${bestPerson.j + 1}")
}

fun readIntegers(count: Int) = readln().split(" ").mapNotNull { it.toIntOrNull() }.take(count)

data class Person(var i: Int, var j: Int, var strength: Int) : Comparable<Person> {
    fun update(newi: Int, newj: Int, newStrength: Int) {
        i = newi
        j = newj
        strength = newStrength
    }

    override fun compareTo(other: Person) = this.strength.compareTo(other.strength)
}