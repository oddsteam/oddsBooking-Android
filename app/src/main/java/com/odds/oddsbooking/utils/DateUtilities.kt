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

    fun getTimeSlot(startTime: String, endTime: String): Array<String> {
        var timeSlot = arrayOf<String>()
        val startTimeArray = startTime.split(":")
        val endTimeArray = endTime.split(":")
        val startHr = startTimeArray[0].toInt()
        val startMin = startTimeArray[1].toInt()
        val endHr = endTimeArray[0].toInt()
        val endMin = endTimeArray[1].toInt()
        if (startHr == endHr) {
            if (startMin == 0) timeSlot += "$startHr:00"
            if (startMin == 30) timeSlot += "$startHr:30"
        } else {
            //TODO: write Test
            for (i in startHr..endHr) {
                if (i == startHr) {
                    if (startMin == 0) timeSlot += "$i:00"
                    timeSlot += "$i:30"
                } else if (i != endHr) {
                    timeSlot += "$i:00"
                    timeSlot += "$i:30"
                } else {
                    timeSlot += "$i:00"
                    if (endMin == 30) timeSlot += "$i:30"
                }
            }
        }
        return timeSlot
    }

}