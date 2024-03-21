package examples

// Решето Эратосфена
// Поиск всех простых числе от 1 до N
fun main() {
    val n = 10_000_000
    val resheto = Resheto(n)
    var prev = -1
    (2..n).filter { resheto.isPrimal(it) == true }.forEach {
        if (it == prev + 2) println("$prev and $it")
        prev = it
    }
}

class Resheto(private val maxValue: Int) {
    private val array = BooleanArray(maxValue + 1) { true }

    init {
        array[0] = false
        array[1] = false
        for (k in 2..maxValue)
            if (array[k])
                for (m in 2 * k..maxValue step k)
                    array[m] = false
    }

    fun isPrimal(value: Int) = if (value < 2 || value > maxValue) null else array[value]

}