/* D. Слоны и ладьи

На шахматной доске стоят слоны и ладьи, необходимо посчитать, сколько клеток не бьется ни одной из фигур.

Шахматная доска имеет размеры 8 на 8. Ладья бьет все клетки горизонтали и вертикали, проходящих через клетку,
где она стоит, до первой встретившейся фигуры. Слон бьет все клетки обеих диагоналей, проходящих через клетку,
 где он стоит, до первой встретившейся фигуры.

Формат ввода
В первых восьми строках ввода описывается шахматная доска. Первые восемь символов каждой из этих строк описывают
состояние соответствующей горизонтали: символ B (заглавная латинская буква) означает, что в клетке стоит слон,
 символ R — ладья, символ * — что клетка пуста. После описания горизонтали в строке могут идти пробелы, однако длина
 каждой строки не превышает 250 символов. После описания доски в файле могут быть пустые строки.

Формат вывода
Выведите количество пустых клеток, которые не бьются ни одной из фигур.
 */

/*
fun main() {
    val chessDesk = ChessDesk()
    chessDesk.calcSafeCells().also { println(it) }
}
*/
class ChessDesk {
    enum class Type(val sym: Char) {
        Void('*'),
        Rook('R'),
        Bishop('B')
    }

    private val mapper = Type.entries.associateBy { it.sym }
    private val cells = (1..8).flatMap { readln().mapNotNull { ch -> mapper[ch] }.take(8) }
    fun calcSafeCells() =
        cells.asSequence()
            .withIndex()
            .filter { it.value == Type.Void }
            .filter {
                val x = it.index % 8
                val y = it.index / 8
                !beatenByRook(x, y) && !beatenByBishop(x, y)
            }
            .count()

    private fun beatenByRook(x: Int, y: Int): Boolean {
        for (i in 1..7) when (get(x + i, y)) {
            Type.Void -> Unit
            Type.Rook -> return true
            Type.Bishop, null -> break
        }
        for (i in 1..7) when (get(x - i, y)) {
            Type.Void -> Unit
            Type.Rook -> return true
            Type.Bishop, null -> break
        }
        for (i in 1..7) when (get(x, y + i)) {
            Type.Void -> Unit
            Type.Rook -> return true
            Type.Bishop, null -> break
        }
        for (i in 1..7) when (get(x, y - i)) {
            Type.Void -> Unit
            Type.Rook -> return true
            Type.Bishop, null -> break
        }
        return false
    }
    private fun beatenByBishop(x: Int, y: Int): Boolean {
        for (i in 1..7) when (get(x + i, y + i)) {
            Type.Void -> Unit
            Type.Bishop -> return true
            Type.Rook, null -> break
        }
        for (i in 1..7) when (get(x + i, y - i)) {
            Type.Void -> Unit
            Type.Bishop -> return true
            Type.Rook, null -> break
        }
        for (i in 1..7) when (get(x - i, y + i)) {
            Type.Void -> Unit
            Type.Bishop -> return true
            Type.Rook, null -> break
        }
        for (i in 1..7) when (get(x - i, y - i)) {
            Type.Void -> Unit
            Type.Bishop -> return true
            Type.Rook, null -> break
        }
        return false
    }
    private fun get(x: Int, y: Int): Type? {
        if (x !in 0..7 || y !in 0..7) return null
        return cells[x + 8 * y]
    }
}

/* Примеры
-49-
********
********
*R******
********
********
********
********
********

-54-
********
********
******B*
********
********
********
********
********

-40-
********
*R******
********
*****B**
********
********
********
********
 */
