/* B. Футбольный комментатор

Раунд плей-офф между двумя командами состоит из двух матчей. Каждая команда проводит по одному матчу «дома»
и «в гостях». Выигрывает команда, забившая большее число мячей. Если же число забитых мячей совпадает, выигрывает
команда, забившая больше мячей «в гостях». Если и это число мячей совпадает, матч переходит в дополнительный тайм
или серию пенальти.

Вам дан счёт первого матча, а также счёт текущей игры (которая ещё не завершилась). Помогите комментатору сообщить,
сколько голов необходимо забить первой команде, чтобы победить, не переводя игру в дополнительное время.

Формат ввода
В первой строке записан счёт первого мачта в формате G1:G2, где G1 — число мячей, забитых первой командой,
а G2 — число мячей, забитых второй командой.
Во второй строке записан счёт второго (текущего) матча в аналогичном формате. Все числа в записи счёта не превышают 5.
В третьей строке записано число 1, если первую игру первая команда провела «дома», или 2, если «в гостях».

Формат вывода
Выведите единственное целое число — необходимое количество мячей.
*/
/*
fun main() {
    val (a1, b1) = readIntegers(2)
    val (a2, b2) = readIntegers(2)
    val homeGame = readIntegers(1).first()
    // Все числа в записи счёта не превышают 5.
    // Значит наихудший расклад для первой команды - 0:5, 0:5
    val goalsToWin = (0..11).first { isWin(a1, b1, a2 + it, b2, firstHome = homeGame == 1) }
    println(goalsToWin)
}

fun readIntegers(count: Int) = readln().split(":").mapNotNull { it.toIntOrNull() }.take(count)
*/
fun isWin(a1: Int, b1: Int, a2: Int, b2: Int, firstHome: Boolean): Boolean {
    val compare = (a1 + a2).compareTo(b1 + b2)
    return when {
        compare < 0 -> false
        compare > 0 -> true
        else -> if (firstHome) a2 > b1 else a1 > b2
    }
}