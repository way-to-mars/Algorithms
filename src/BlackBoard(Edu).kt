import examples.IntArray2D

fun main() {
    val value: Result<Int> = runCatching { "12".toInt() }
}



class lazyRandom(){
    val value: Int by lazy<Int>{ 0 }
}

@JvmInline
value class CustomerID(private val id: Int){
    companion object{
        @JvmStatic
        fun fromString(str: String): CustomerID?{
            val parsedInt = str.toIntOrNull() ?: return null
            return CustomerID(parsedInt)
        }
    }
    init {
        require(id > 0)
    }
    override fun toString() = "CustomerID($id)"
    fun toInt() = id
}

@JvmName("specialMaxOnListOfString")
fun List<String>.specialMax(): Int {
    return this.maxOf { it.length }
}

@JvmName("specialMaxOnListOfInt")
fun List<Int>.specialMax(): Int {
    return this.maxOf { if (it >= 0) it else -1 }
}

