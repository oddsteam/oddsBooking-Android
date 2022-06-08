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
        when {
            email.isEmpty() -> {
                // TODO: change String type to int & declaration @String
                view.onEmailError("empty email")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                // TODO: change String type to int & declaration @String
                view.onEmailError("email invalid")
            }
            else -> {
                view.onEmailValid()
            }

        }
    }
    fun phoneValidator(phone: String){
       when {
           phone.isEmpty() ->{
               view.onPhoneError("phone number is empty")
           }
           !Patterns.PHONE.matcher(phone).matches() -> {
               view.onPhoneError("phone number invalid")
           }else -> {
               view.onPhoneValid()
           }
        }

    }


    fun validatePhoneNumber(phoneNumber: String) {

    }

    fun validateRoom(room: String) {

    }

    fun validateReason(reason: String) {

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