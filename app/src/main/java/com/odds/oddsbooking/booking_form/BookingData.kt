package com.odds.oddsbooking.booking_form

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingData(
    var fullName: String,
    var email: String,
    var phoneNumber: String,
    var room: String,
    var reason: String,
    var fromDate: String,
    var fromTime: String,
    var toDate: String,
    var toTime: String
) : Parcelable {

    fun isValid(): Boolean {
        return fullName.isNotEmpty() && email.isNotEmpty() && phoneNumber.isNotEmpty() && room.isNotEmpty() && reason.isNotEmpty() && fromDate.isNotEmpty() && fromTime.isNotEmpty() && toDate.isNotEmpty() && toTime.isNotEmpty()
    }

}