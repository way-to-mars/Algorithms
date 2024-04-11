package examples

import printLog

fun main() {

    val polinome = GornerScheme(listOf(5.0, 2.0, -3.0, 1.0, 5.0))

    polinome(0.0).printLog()
    polinome(1.0).printLog()
    polinome(2.0).printLog()

}

class GornerScheme(coefs: List<Double>) {
    val a = coefs.reversed()
    operator fun invoke(x: Double) = a.reduce { acc, d -> acc * x + d }
}