package lesson3

// I. Играйте в футбол!
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException

fun main() {
    val file = File("input.txt")
    val soccerPedia = SoccerPedia()
    try {
        BufferedReader(FileReader(file)).use { br ->
            br.lines().forEach {
                soccerPedia.parse(it)
            }
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}

class SoccerPedia {
    inner class TeamInfo {
        var games = 0
        var goals = 0
        var opens = 0
    }

    inner class PlayerInfo(val teamName: String) {
        var goals = 0
        var opens = 0
        val minutes = mutableListOf<Int>()
    }

    private val teams = mutableMapOf<String, TeamInfo>()
    private val players = mutableMapOf<String, PlayerInfo>()

    private val gameStrings = mutableListOf<String>()
    private var goalsToRead = 0
    private val parsingGame
        get() = goalsToRead > 0

    private fun startGame(gameInfo: String) {
        gameStrings.clear()
        gameStrings.add(gameInfo)
        val (score1, score2) = gameInfo.split(' ').last().split(':').map(String::toInt)
        goalsToRead = score1 + score2
        if (!parsingGame) finalizeGame()
    }

    private fun readGoal(goalInfo: String) {
        gameStrings.add(goalInfo)
        goalsToRead--
        if (!parsingGame) finalizeGame()
    }

    private fun finalizeGame() {
        class GoalInfo(str: String) {
            val name: String
            val minute: Int
            init {
                val i = str.lastIndexOf(' ')
                minute = str.drop(i + 1).dropLast(1).toInt()
                name = str.take(i)
            }
        }
        val (_, team1, _, team2) = gameStrings[0].split('\"')
        val (score1, score2) = gameStrings[0].split(' ').last().split(':').map(String::toInt)

        if (!teams.containsKey(team1)) teams[team1] = TeamInfo()
        if (!teams.containsKey(team2)) teams[team2] = TeamInfo()

        teams[team1]!!.games++
        teams[team1]!!.goals += score1
        teams[team2]!!.games++
        teams[team2]!!.goals += score2

        val goals = gameStrings.drop(1).map(::GoalInfo)
        val firstGoal = goals.minByOrNull { it.minute }
        val goals1 = goals.take(score1)
        val goals2 = goals.drop(score1)

        if (score1 > 0 && goals1.any { it.minute == firstGoal?.minute }) teams[team1]!!.opens++
        if (score2 > 0 && goals2.any { it.minute == firstGoal?.minute }) teams[team2]!!.opens++

        goals1.forEach {
            if (!players.containsKey(it.name)) players[it.name] = PlayerInfo(team1)
            players[it.name]!!.goals++
            players[it.name]!!.minutes.add(it.minute)
            if (it.minute == firstGoal!!.minute) players[it.name]!!.opens++
        }
        goals2.forEach {
            if (!players.containsKey(it.name)) players[it.name] = PlayerInfo(team2)
            players[it.name]!!.goals++
            players[it.name]!!.minutes.add(it.minute)
            if (it.minute == firstGoal!!.minute) players[it.name]!!.opens++
        }
    }

    private fun answer(question: String) {
        when {
            question.startsWith("Total goals for ") -> totalGoalsForTeam(question)
            question.startsWith("Mean goals per game for ") -> meanGoalForTeam(question)
            question.startsWith("Score opens by ") -> opensBy(question)
            question.startsWith("Total goals by ") -> totalGoalsByPlayer(question)
            question.startsWith("Mean goals per game by ") -> meanGoalsByPlayer(question)
            question.startsWith("Goals on ") -> goalsOnByPlayer(question)
            else -> println("Unknown request!!!")
        }
    }

    private fun totalGoalsForTeam(question: String) {
        val teamName = question.split('\"')[1]
        val goals = teams[teamName]?.goals ?: 0
        println(goals)
    }

    private fun meanGoalForTeam(question: String) {
        val teamName = question.split('\"')[1]
        if (teams.containsKey(teamName)) {
            println(teams[teamName]!!.goals.toDouble() / teams[teamName]!!.games)
        } else
            println(0)
    }

    private fun opensBy(question: String) {
        val name = question.drop("Score opens by ".length)
        if (name.first() == '\"' && name.last() == '\"'){ // by team
            val teamName = name.trim('\"')
            if (teams.containsKey(teamName)) {
                println(teams[teamName]!!.opens)
            } else println(0)
            return
        }
        if (players.containsKey(name)) {  // by player
            println(players[name]!!.opens)
        } else println(0)
    }

    private fun totalGoalsByPlayer(question: String) {
        val name = question.drop("Total goals by ".length)
        val goals = players[name]?.goals ?: 0
        println(goals)
    }

    private fun meanGoalsByPlayer(question: String) {
        val name = question.drop("Mean goals per game by ".length)

        if (players.containsKey(name) && teams.containsKey(players[name]!!.teamName)) {
            val goals = players[name]!!.goals
            val games = teams[players[name]!!.teamName]!!.games
            println(goals.toDouble() / games)
        } else println(0)
    }

    private fun goalsOnByPlayer(question: String) {
        val index = question.indexOf(" by ")
        val name = question.drop(index + 4)

        if (!players.containsKey(name)) {
            println(0)
            return
        }

        val minute = question.split(" ")[3].toInt()

        when {
            question.startsWith("Goals on minute") ->
                println(players[name]!!.minutes.count { it == minute })
            question.startsWith("Goals on first") ->
                println(players[name]!!.minutes.count { it <= minute })
            question.startsWith("Goals on last") ->
                println(players[name]!!.minutes.count { it > 90 - minute })
            else -> throw IllegalArgumentException("Unable to parse $question")
        }
    }

    fun parse(message: String) {
        when {
            message.contains(':') -> startGame(message)
            parsingGame -> readGoal(message)
            else -> answer(message)
        }
    }
}