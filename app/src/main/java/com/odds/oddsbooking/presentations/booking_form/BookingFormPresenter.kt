package com.odds.oddsbooking.presentations.booking_form

import android.util.Patterns
import com.odds.oddsbooking.interfaces.BookingData


class BookingFormPresenter {
    private lateinit var view: BookingFormView

    fun attachView(view: BookingFormView) {
        this.view = view
    }

    interface BookingFormView {
        fun onNameAutoFormat(name: String)
        fun enablePreviewButton()
        fun disablePreviewButton()
        fun onValidateNameError(errMsg: String)
        fun onValidateNameSuccess()
        fun onValidateEmailError(errMsg: String)
        fun onValidateEmailSuccess()
        fun onValidatePhoneNumberError(errMsg: String)
        fun onValidatePhoneNumberSuccess()
        fun onValidateRoomError(errMsg: String)
        fun onValidateRoomSuccess()
        fun onValidateReasonError(errMsg: String)
        fun onValidateReasonSuccess()
        fun onValidateFromDateError(errMsg: String)
        fun onValidateFromDateSuccess()
        fun onValidateFromTimeError(errMsg: String)
        fun onValidateFromTimeSuccess()
        fun onValidateToDateError(errMsg: String)
        fun onValidateToDateSuccess()
        fun onValidateToTimeError(errMsg: String)
        fun onValidateToTimeSuccess()
    }

    fun validateFullName(fullName: String): Boolean {
        when {
            fullName.isEmpty() -> {
                view.onValidateNameError("fullName can't be empty")
                return false
            }
            else -> {
                view.onValidateNameSuccess()
            }
        }
        return true
    }

    fun validateEmail(email: String) : Boolean {
        when {
            email.isEmpty() -> {
                view.onValidateEmailError("Email can't be empty")
                return false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.onValidateEmailError("Format email error")
                return false
            }
            else -> {
                view.onValidateEmailSuccess()
            }
        }
        return true
    }

    fun validatePhoneNumber(phoneNumber: String): Boolean {
        when {
            phoneNumber.isEmpty() -> {
                view.onValidatePhoneNumberError("Phone number can't be empty")
                return false
            }
            !Regex("^0[9, 8, 6, 2][0-9]{8}\$").matches(phoneNumber) -> {
                view.onValidatePhoneNumberError("Format phone number error")
                return false
            }
            else -> {
                view.onValidatePhoneNumberSuccess()
            }
        }
        return true
    }

    fun validateRoom(room: String) : Boolean {
        when {
            room.isEmpty() -> {
                view.onValidateRoomError("Please enter room")
                return false
            }
            else -> {
                view.onValidateRoomSuccess()
            }
        }
        return true
    }

    fun validateReason(reason: String) : Boolean {
        when {
            reason.isEmpty() -> {
                view.onValidateReasonError("Reason can't be empty")
                return false
            }
            else -> {
                view.onValidateReasonSuccess()
            }
        }
        return true
    }

    fun validateFromDate(fromDate: String): Boolean {
        when {
            fromDate.isEmpty() -> {
                view.onValidateFromDateError("From date can't be empty")
                return false
            }
            else -> {
                view.onValidateFromDateSuccess()
            }
        }
        return true
    }

    fun validateFromTime(fromTime: String): Boolean {
        when {
            fromTime.isEmpty() -> {
                view.onValidateFromTimeError("From time can't be empty")
                return false
            }
            else -> {
                view.onValidateFromTimeSuccess()
            }
        }
        return true
    }

    fun validateToDate(toDate: String) : Boolean {
        when {
            toDate.isEmpty() -> {
                view.onValidateToDateError("To date can't be empty")
                return false
            }
            else -> {
                view.onValidateToDateSuccess()
            }
        }
        return true
    }

    fun validateToTime(toTime: String) : Boolean {
        when {
            toTime.isEmpty() -> {
                view.onValidateToTimeError("To time can't be empty")
                return false
            }
            else -> {
                view.onValidateToTimeSuccess()
            }
        }
        return true
    }

    fun validateForm(data: BookingData) {
        when {
            data.isValid() -> {
                view.enablePreviewButton()
            }
            else -> {
                view.disablePreviewButton()
            }
        }
    }

    fun autoFormatName(name: String) {
        view.onNameAutoFormat(name)
    }

}