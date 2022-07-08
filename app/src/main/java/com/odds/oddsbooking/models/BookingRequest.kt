package com.odds.oddsbooking.models

data class BookingRequest(
    //TODO: add default value
    val fullName: String = "",
    val email: String = "",
    val phoneNumber: String = "",
    val room: String = "",
    val reason: String = "",
    val startDate: String = "",
    val endDate: String = "",
    var status: Boolean = false
)
