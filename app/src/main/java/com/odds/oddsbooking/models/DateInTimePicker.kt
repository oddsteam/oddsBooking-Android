package com.odds.oddsbooking.models

import java.util.*

data class DateInTimePicker(
    val datePickerType: DateInTimePickerType,
    val minDate: Long,
    val maxDate: Long?,
    val timePicked: String
) {
    val currentCalendar: CalendarDate
        get() = if (timePicked.isNotEmpty()) {
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

    val minDateCalendar: Calendar
        get() {
            var calendar = currentCalendar.calendar
            calendar.timeInMillis = minDate
            return calendar
        }

    val maxDateCalendar: Calendar
        get() {
            var calendar = currentCalendar.calendar
            if (maxDate != null) {
                calendar.timeInMillis = maxDate
            }
            return calendar
        }


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

enum class DateInTimePickerType {
    FROM_DATE,
    TO_DATE
}