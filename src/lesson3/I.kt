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

class SoccerPedia{
    private var goalsCounter = -1
    private val parsingGame
        get() = goalsCounter > 0

    private fun startGame(gameInfo: String){
        val (_, team1, _, team2) = gameInfo.split('\"')
        val (score1, score2) = gameInfo.split(' ').last().split(':').map(String::toInt)
        goalsCounter = score1 + score2

        println("Teams $team1 - $team2")
        println("Score $score1:$score2")
    }

    private fun parseGoal(goalInfo: String){
        val i = goalInfo.lastIndexOf(' ')
        val minute = goalInfo.drop(i + 1).dropLast(1).toInt()
        val name = goalInfo.take(i)
        println("($goalsCounter) >> $name scored at $minute minute")
        goalsCounter--
    }

    fun parse(message: String){
        when{
            message.contains(':') -> startGame(message)
            parsingGame == true -> parseGoal(message)
            else -> println(message)
        }
    }
}