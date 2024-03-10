package lesson1/*
На вход подаётся три целых числа x, y, p (1 ≤ x, y, p ≤ 5000) — количество ваших солдат на старте игры,
количество очков здоровья казармы и количество производимых за раунд казармой солдат, соответственно.
Каждое число расположено в новой строке.
 */

fun main() {
    val x = readln().toInt()
    val y = readln().toInt()
    val p = readln().toInt()

    playGame(listOf(GameState(x, y, p, 0, 0))).also(::println)
}

tailrec fun playGame(states: List<GameState>): Int {
    if (states.isEmpty()) return -1
    states.firstOrNull { it.isWin() }?.also { return it.index }
    return playGame(states.flatMap { listOfNotNull(it.byStrategy1(), it.byStrategy2()) }.distinct())
}

infix fun Int.splitBy(value: Int) = if (this < value) (this to 0) else (value to this - value)

data class GameState(
    val mySoldiers: Int,
    val barracksHP: Int,
    val prodRate: Int,
    val enemySoldiers: Int,
    val index: Int
) {
    fun byStrategy1(): GameState? {  // hit the barrack first
        if (mySoldiers < barracksHP) return null  // don't hit barrack until we're able to destroy it within a round
        val (barrackDmg, soldiersDmg) = mySoldiers splitBy barracksHP
        val y = barracksHP - barrackDmg
        val e = enemySoldiers - soldiersDmg
        val eNext = if (y > 0) e + prodRate else e
        return buildOrNull(mySoldiers - e, y, eNext)
    }

    fun byStrategy2(): GameState? {  // hit enemy soldiers first
        val (soldiersDmg, barrackDmg) = mySoldiers splitBy enemySoldiers
        val y = barracksHP - barrackDmg
        val e = enemySoldiers - soldiersDmg
        val eNext = if (y > 0) e + prodRate else e
        return buildOrNull(mySoldiers - e, y, eNext)
    }

    private fun buildOrNull(mySoldiers: Int, barracksHP: Int, enemySoldiers: Int): GameState? {
        if (mySoldiers <= 0) return null
        if (mySoldiers == this.mySoldiers &&
            barracksHP == this.barracksHP &&
            enemySoldiers == this.enemySoldiers
        ) return null
        return GameState(mySoldiers, barracksHP, prodRate, enemySoldiers, index + 1)
    }

    fun isWin() = barracksHP <= 0 && enemySoldiers <= 0
    override fun toString(): String {
        return "[$mySoldiers, $barracksHP, $enemySoldiers]"
    }
}