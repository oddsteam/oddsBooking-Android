package com.odds.oddsbooking.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtilities {

    private fun checkDay(
        value: String,
        formatter: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.US)
    ): String {
        val date = formatter.parse(value)!!
        // return day of week (Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)
        return SimpleDateFormat("EEEE", Locale.US).format(date)
    }

    fun isSaturday(date: String): Boolean {
        return checkDay(date) == "Saturday"
    }

    private fun isSunday(date: String): Boolean {
        return checkDay(date) == "Sunday"
    }

    fun isWeekend(date: String): Boolean {
        return isSunday(date) || isSaturday(date)
    }

    fun isSameDate(fromDate: String, toDate: String): Boolean {
        return fromDate == toDate
    }

    fun getDateFormatter(year: Int, month: Int, day: Int): String {
        return String.format("%d/%02d/%02d", year, month + 1, day)
    }

    fun dateTimeGeneralFormat(dateTime: String): String {
        val (year, month, day) = dateTime.split("/").toTypedArray()
        return "${year}-${month}-${day}"
    }
}