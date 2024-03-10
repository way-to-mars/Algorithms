package lesson1

import lesson1.Calendar.toDayOfWeek

fun main() {
    val holidaysCount = readln().toInt()
    val year = readln().toInt()
    val holidaysList = (1..holidaysCount).map {
        readln().split(" ").run {
            Calendar.monthNumber(this[1]) to this[0].toInt()
        }
    }
    val firstDayOfWeek = readln()

    val workdaysByDayOfWeek = Calendar.listWorkdaysByDayOfWeek(year, firstDayOfWeek, holidaysList)
    workdaysByDayOfWeek.withIndex().maxBy { (_, days) -> days }.index.toDayOfWeek().also(::println)
    workdaysByDayOfWeek.withIndex().minBy { (_, days) -> days }.index.toDayOfWeek().also(::println)
}

object Calendar {
    private val leapYear = listOf(31, 29, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private val ordinaryYear = listOf(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31)
    private val daysOfWeek = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday")
    private val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    private fun monthsInYear(year: Int): List<Int> {
        if (year % 400 == 0) return leapYear
        if (year % 4 == 0 && year % 100 != 0) return leapYear
        return ordinaryYear
    }
    private fun countDays(year: Int) = monthsInYear(year).sum()
    fun monthNumber(name: String): Int = months.indexOfFirst { it == name }
    private fun dayOfWeekNumber(name: String): Int = daysOfWeek.indexOfFirst { it == name }
    fun Int.toDayOfWeek() = daysOfWeek[this]
    fun listWorkdaysByDayOfWeek(year: Int, firstDay: String, holidaysList: List<Pair<Int, Int>>): IntArray {
        val first = dayOfWeekNumber(firstDay)
        val cal = monthsInYear(year)
        val workdaysByDayOfWeek = (0..<countDays(year)).fold(IntArray(7) { 0 }) { arr, i ->
            arr[(first + i) % 7]++
            arr
        }
        holidaysList.fold(workdaysByDayOfWeek) { arr, pair ->
            val dayOfWeek = ((cal.take(pair.first).sum() + pair.second - 1) + first) % 7
            arr[dayOfWeek]--
            arr
        }
        return workdaysByDayOfWeek
    }
}