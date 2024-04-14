package examples


@Suppress("SpellCheckingInspection")
fun main() {
    val correctList = listOf(
        "[](){}<>",
        "((()))",
        "((([])))<{}>",
        "[g]h(jjh)hk{}<>",
        "(gs(dfgh(fjdh)kgf))",
        "((sdfg[sgd]gdfs)dfsg)<gfd{}>"
    )

    val incorrectList = listOf(
        "({)}",
        "((}]",
        "[])",
        "<([]{}>",
        "(tg{)fgfdgh}fgdh",
        "(gdgxgf(dfjjfdh}]",
        "[4536456]dfhj)",
        ".fdlgjhp;sjhtg<5g(6h[7j]345yy{h6g53}54h>65h",
    )

    require(!correctList.any { !isCorrectBraces(it) }) { "1" }
    require(!incorrectList.any { isCorrectBraces(it) }) { "2" }
    println("End")
}

/**
 * Баланс скобок или
 * Корректность скобочной последовательности
 * @return `true` если строка корректная
 */
fun isCorrectBraces(str: String): Boolean {
    val stack = mutableListOf<Char>()
    val pop: () -> Char = { stack.removeLast() }
    val push: (Char) -> Unit = { stack.add(it) }
    val bracesMap = hashMapOf(
        '(' to ')',
        '[' to ']',
        '<' to '>',
        '{' to '}',
    )
    val openingBraces = bracesMap.keys.toHashSet()
    val closingBraces = bracesMap.values.toHashSet()

    for (i in str.indices) {
        when (val char = str[i]) {
            in openingBraces -> push(char)
            in closingBraces -> {
                if (stack.isEmpty()) return false
                if (bracesMap[pop()] != char) return false
            }
        }
    }
    return stack.isEmpty()
}