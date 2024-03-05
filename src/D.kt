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

Пример ввода:
********
*R******
********
*****B**
********
********
********
********
 */

class ChessDesk {
    enum class Type(val sym: Char) {
        Void('*'),
        Rook('R'),
        Bishop('B')
    }

    private val mapper = Type.entries.associateBy { it.sym }
    private val cells = (1..8).flatMap { readln().mapNotNull { ch -> mapper[ch] }.take(8) }

    init {
        val rows = BooleanArray(8){ false}
        val colums = BooleanArray(8){ false}
        val diagonalA = BooleanArray(16){ false}
        val diagonalB = BooleanArray(16){ false}

        cells.withIndex().forEach { item ->
            val x = item.index / 8
            val y = item.index % 8
            when (item.value)
            {
                Type.Rook -> {
                    rows[x] = true
                    colums[y] = true
                }
                Type.Bishop -> {
                    diagonalA[x + y] = true
                    diagonalB[8 + x - y] = true
                }
                else -> Unit
            }
        }

        println("rows = ${rows.contentToString()}")
        println("columns = ${colums.contentToString()}")
        println("diagonalA = ${diagonalA.contentToString()}")
        println("diagonalB = ${diagonalB.contentToString()}")

    }
}
