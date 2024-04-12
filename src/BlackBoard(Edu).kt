fun main(){
    val iarr = IntArray(10)
    iarr.printLog()

    val arri = Array(10) { it }
    arri.printLog()

    val listi: java.util.ArrayList<Int> = ArrayList<Int>((1..10).map { it })
    listi.printLog()

    val list: Collection<Int> = listOf(0,1,2,3,4,5,6,7,8,9)
    list.printLog()
}