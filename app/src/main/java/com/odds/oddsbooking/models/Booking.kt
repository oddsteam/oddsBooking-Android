package com.odds.oddsbooking.models

data class Booking(
    val fullName: String, val email: String,
    val phoneNumber: String, val room: String,
    val reason: String, val startDate: String,
    val endDate: String, val status: Boolean,
)
