package com.odds.oddsbooking.utils

import java.text.SimpleDateFormat
import java.util.*

class DateUtilities {

    companion object {
        fun checkDay(value: String, formatter: SimpleDateFormat = SimpleDateFormat("yyyy/MM/dd", Locale.US)): String {
            val date = formatter.parse(value)!!
            // return day of week (Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)
            return SimpleDateFormat("EEEE", Locale.US).format(date)
        }

        fun isSaturday(date: String): Boolean {
            return checkDay(date) == "Saturday"
        }

        fun isSunday(date: String): Boolean {
            return checkDay(date) == "Sunday"
        }

        fun isWeekend(date: String): Boolean {
            return isSunday(date) || isSaturday(date)
        }

    }
}