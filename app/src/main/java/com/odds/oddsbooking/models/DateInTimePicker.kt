package com.odds.oddsbooking.models

import java.util.*

//FromDate -> DateInTimePicker
data class DateInTimePicker(
    val type : DateInTimePickerType, //name of datePicker (flag)
    val minDate: Long,
    val maxDate: Long?
){
    fun getCurrentCalendar(timePicked: String): CalendarDate {
        var calendarDate = if (timePicked.isNotEmpty()) {
            val dates = timePicked.split("/")
            val years = dates[0].toInt()
            val months = dates[1].toInt() - 1
            val days = dates[2].toInt()
            CalendarDate(Calendar.getInstance(), years, months, days)
        } else {
            val years = Calendar.getInstance().get(Calendar.YEAR)
            val months = Calendar.getInstance().get(Calendar.MONTH)
            val days = Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
            CalendarDate(Calendar.getInstance(), years, months, days)
        }

        return calendarDate
    }
}

enum class DateInTimePickerType{
    FROM_DATE,
    TO_DATE
}