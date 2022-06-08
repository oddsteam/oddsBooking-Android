package com.odds.oddsbooking.booking_form

import android.util.Patterns


class BookingFormPresenter {
    private lateinit var view: BookingFormView

    fun attachView(view: BookingFormView) {
        this.view = view
    }

    interface BookingFormView {
        fun onNameError(errMsg: String)
        fun onNameValid()
        fun onNameAutoFormat(name : String)
        fun onEmailError(errMsg: String)
        fun onEmailValid()
        fun onPhoneError(errMsg: String)
        fun onPhoneValid()
        fun onRoomError(errMsg: String)
        fun onRoomValid()
        fun onReasonError(errMsg: String)
        fun onReasonValid()
        fun onFromDateError(errMsg: String)
        fun onFromDateValid()
        fun onFromTimeError(errMsg: String)
        fun onFromTimeValid()
        fun onToDateError(errMsg: String)
        fun onToDateValid()
        fun onToTimeError(errMsg: String)
        fun onToTimeValid()
    }

    fun validateName(name: String) {
        when {
            name.isEmpty() -> {
                view.onNameError("empty name")
            }
            else -> {
                view.onNameValid()
            }
        }
    }
    fun autoFormatName(name : String){
        view.onNameAutoFormat(name)
    }

    fun validateEmail(email: String) {
        // TODO: change String type to int & declaration @String
        when {
            email.isEmpty() -> {
                view.onEmailError("empty email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.onEmailError("email invalid")
            }
            else -> {
                view.onEmailValid()
            }

        }
    }

    fun validatePhoneNumber(phone: String) {
        when {
            phone.isEmpty() -> {
                view.onPhoneError("phone number is empty")
            }
            !(phone.startsWith("08") || phone.startsWith("06") || phone.startsWith("02") || phone.startsWith("09")) -> {
                view.onPhoneError("phone number invalid (should start with 06,08,09)")
            }
            phone.length != 10 -> {
                view.onPhoneError("phone number must be 10 digits")
            }
            else -> {
                view.onPhoneValid()
            }
        }

    }


    fun validateRoom(room: String) {
        when {
            room.isEmpty() -> {
                view.onRoomError("empty room")
            }
            else -> {
                view.onRoomValid()
            }
        }
    }

    fun validateReason(reason: String) {
        when {
            reason.isEmpty() -> {
                view.onReasonError("empty reason")
            }
            else -> {
                view.onReasonValid()
            }
        }
    }

    fun validateFromDate(fromDate: String) {
        when {
            fromDate.isEmpty() -> {
                view.onFromDateError("empty From Date")
            }
            else -> {
                view.onFromDateValid()
            }
        }
    }

    fun validateFromTime(fromTime: String) {
        when {
            fromTime.isEmpty() -> {
                view.onFromTimeError("empty from time")
            }
            else -> {
                view.onFromTimeValid()
            }
        }

    }

    fun validateToDate(toDate: String) {
        when {
            toDate.isEmpty() -> {
                view.onToDateError("empty To Date")
            }
            else -> {
                view.onToDateValid()
            }
        }
    }

    fun validateToTime(toTime: String) {
        when {
            toTime.isEmpty() -> {
                view.onToTimeError("empty To Time")
            }
            else -> {
                view.onToTimeValid()
            }
        }
    }


}