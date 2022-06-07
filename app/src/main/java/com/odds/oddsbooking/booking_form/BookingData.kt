package com.odds.oddsbooking.booking_form

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingData(
    val fullName: String,
    val email: String,
    val phoneNumber: String,
    val room: String,
    val reason: String,
    val fromDate: String,
    val fromTime: String,
    val toDate: String,
    val toTime: String
) : Parcelable