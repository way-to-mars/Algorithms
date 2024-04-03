package examples

fun main() {
    for (i in 0..30)
        println(Factorial.get(i).groupDigits())


}

class Factorial {
    companion object {
        private val values = mutableListOf(1L)  // 0! = 1 by convention
        private var overflow = false
        @JvmStatic
        fun get(n: Int): Long {
            if (n >= values.size) update(n)
            return values[n]
        }

        @Synchronized
        private fun update(n: Int) {
            var x = values.size
            while (x <= n) {
                if (overflow) values.add(-1L)
                else {
                    val last = values.last()
                    if (Long.MAX_VALUE / last < x) {
                        overflow = true
                        values.add(-1L)
                    } else values.add(x * values.last())
                }
                x++
            }
        }
    }
}

fun Long.groupDigits(): String = this.toString().reversed().chunked(3).joinToString(separator = "'").reversed()