package com.odds.oddsbooking.models
//FromDate -> DateInTimePicker
data class DateInTimePicker(
    val type : DateInTimePickerType, //name of datePicker (flag)
    val minDate: Long,
    val maxDate: Long?
)

enum class DateInTimePickerType{
    FROM_DATE,
    TO_DATE
}