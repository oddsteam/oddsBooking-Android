package com.odds.oddsbooking.models

import android.os.Parcelable
import android.util.Patterns
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingData(
    var fullName: String = "",
    var email: String = "",
    var phoneNumber: String = "",
    var room: String = "",
    var reason: String = "",
    var fromDate: String = "",
    var fromTime: String = "",
    var toDate: String = "",
    var toTime: String = "",
) : Parcelable {

    private fun validateFullName(): Boolean {
        return when {
            fullName.isEmpty() -> false
            else -> true
        }
    }

    private fun validateEmail(): Boolean {
        return when {
            email.isEmpty() -> false
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> false
            else -> true
        }
    }

    private fun validatePhoneNumber(): Boolean {
        return when {
            phoneNumber.isEmpty() -> false
            !Regex("^0[9, 8, 6, 2][0-9]{8}\$").matches(phoneNumber) -> false
            else -> true
        }
    }

    private fun validateRoom(): Boolean {
        return when {
            room.isEmpty() -> false
            else -> true
        }
    }

    private fun validateReason(): Boolean {
        return when {
            reason.isEmpty() -> false
            else -> true
        }
    }

    private fun validateFromDate(): Boolean {
        return when {
            fromDate.isEmpty() -> false
            else -> true
        }
    }

    private fun validateFromTime(): Boolean {
        return when {
            fromTime.isEmpty() -> false
            else -> true
        }
    }

    private fun validateToDate(): Boolean {
        return when {
            toDate.isEmpty() -> false
            else -> true
        }
    }

    private fun validateToTime(): Boolean {
        return when {
            toTime.isEmpty() -> false
            else -> true
        }
    }

    fun validateBookingData(): Boolean {
        return validateFullName()
                && validateEmail()
                && validatePhoneNumber()
                && validateRoom()
                && validateReason()
                && validateFromDate()
                && validateFromTime()
                && validateToDate()
                && validateToTime()
    }

}