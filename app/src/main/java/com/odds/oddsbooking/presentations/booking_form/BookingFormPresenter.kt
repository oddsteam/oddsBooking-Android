package com.odds.oddsbooking.presentations.booking_form

import android.util.Log
import android.util.Patterns
import com.odds.oddsbooking.R
import com.odds.oddsbooking.interfaces.BookingData
import com.wdullaer.materialdatetimepicker.time.Timepoint
import java.text.SimpleDateFormat
import java.util.*


class BookingFormPresenter {
    private lateinit var view: BookingFormView
    private var formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)

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
        fun onValidateFromDateSuccess(timeEnable: Array<Timepoint>)
        fun onValidateFromTimeError(errMsg: String)
        fun onValidateFromTimeSuccess(minDate: Long, maxDate: Long)
        fun onValidateToDateError(errMsg: String)
        fun onValidateToDateSuccess(timeEnable: Array<Timepoint>)
        fun onValidateToTimeError(errMsg: String)
        fun onValidateToTimeSuccess()
    }

    fun validateFullName(fullName: String){
        when {
            fullName.isEmpty() -> {
                view.onValidateNameError("fullName can't be empty")
            }
            else -> {
                view.onValidateNameSuccess()
            }
        }
    }

    fun validateEmail(email: String) {
        when {
            email.isEmpty() -> {
                view.onValidateEmailError("Email can't be empty")
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.onValidateEmailError("Format email error")
            }
            else -> {
                view.onValidateEmailSuccess()
            }
        }
    }

    fun validatePhoneNumber(phoneNumber: String) {
        when {
            phoneNumber.isEmpty() -> {
                view.onValidatePhoneNumberError("Phone number can't be empty")
            }
            !Regex("^0[9, 8, 6, 2][0-9]{8}\$").matches(phoneNumber) -> {
                view.onValidatePhoneNumberError("Format phone number error")
            }
            else -> {
                view.onValidatePhoneNumberSuccess()
            }
        }
    }

    fun validateRoom(room: String) {
        when {
            room.isEmpty() -> {
                view.onValidateRoomError("Please enter room")
            }
            else -> {
                view.onValidateRoomSuccess()
            }
        }
    }

    fun validateReason(reason: String) {
        when {
            reason.isEmpty() -> {
                view.onValidateReasonError("Reason can't be empty")
            }
            else -> {
                view.onValidateReasonSuccess()
            }
        }
    }

    fun validateFromDate(fromDate: String) {
        when {
            fromDate.isEmpty() -> {
                view.onValidateFromDateError("From date can't be empty")
            }
            else -> {
                //on week end
                if (checkDay(fromDate) == "Sunday" || checkDay(fromDate) == "Saturday") {
                    view.onValidateFromDateSuccess(getTimePoint("09:00", "20:59"))
                }
                //on week day
                else {
                    view.onValidateFromDateSuccess(
                        getTimePoint("00:00", "05:59") + getTimePoint(
                            "18:00",
                            "23:59"
                        )
                    )
                }
            }
        }
    }

    fun validateFromTime(fromTime: String, fromDate: String) {
        when {
            fromTime.isEmpty() -> {
                view.onValidateFromTimeError("From time can't be empty")
            }
            else -> {
                val date = formatter.parse(fromDate)
                //on weekend (Saturday)
                if (checkDay(fromDate) == "Saturday") {
                    val minDate: Long = date.time
                    val maxDate: Long = date.time + (24 * 60 * 60 * 1000) // can booking Sunday
                    view.onValidateFromTimeSuccess(
                        minDate, maxDate
                    )
                }
                //on weekend (Sunday)
                else if (checkDay(fromDate) == "Sunday") {
                    val minDate: Long = date.time
                    val maxDate: Long = date.time
                    view.onValidateFromTimeSuccess(
                        minDate, maxDate
                    )
                }
                //on weekday
                else {
                    val startTimeArray = fromTime.split(":")
                    //fromTime >= 18:00, next day can booking (00:00 - 06:00)
                    if (startTimeArray[0].toInt() >= 18) {
                        val minDate: Long = date.time
                        val maxDate: Long = date.time + (24 * 60 * 60 * 1000) // can booking next day
                        view.onValidateFromTimeSuccess(
                            minDate, maxDate
                        )
                    }
                    //fromTime < 18:00, next day can't booking
                    else {
                        val minDate: Long = date.time
                        val maxDate: Long = date.time
                        view.onValidateFromTimeSuccess(
                            minDate, maxDate
                        )
                    }

                }

            }
        }
    }

    fun validateToDate(toDate: String,  fromDate: String, fromTime: String) {
        var toTime = "00:00"
        when {
            toDate.isEmpty() -> {
                view.onValidateToDateError("To date can't be empty")
            }
            else -> {
                val fromTimeArray = fromTime.split(":")

                //TODO: Test check toTime min = 59 -> hour +1
                toTime = "${fromTimeArray[0].toInt()}:${fromTimeArray[1].toInt() + 1}" // toTime equ fromTime + 1 minute
                //same day
                if (fromDate == toDate) {
                    //on weekend
                    if (checkDay(fromDate) == "Sunday" || checkDay(fromDate) == "Saturday") {
                        view.onValidateToDateSuccess(getTimePoint(toTime, "21:00"))
                    }
                    //on weekday
                    else {
                        if (fromTimeArray[0].toInt() >= 18) {
                            view.onValidateToDateSuccess(getTimePoint(toTime, "23:59"))
                        } else {
                            view.onValidateToDateSuccess(getTimePoint(toTime, "06:00"))
                        }
                    }
                }
                //other day
                else {
                    //on weekend
                    if (checkDay(fromDate) == "Saturday" || checkDay(fromDate) == "Saturday") {
                        view.onValidateToDateSuccess(getTimePoint("09:00", "21:00"))
                    }
                    //on weekday
                    else {
                        if (fromTimeArray[0].toInt() >= 18) {
                            view.onValidateToDateSuccess(getTimePoint("00:00", "06:00"))
                        } else {
                            view.onValidateToDateSuccess(
                                getTimePoint("00:00", "06:00") + getTimePoint(
                                    "18:00",
                                    "23:59"
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    fun validateToTime(toTime: String) {
        when {
            toTime.isEmpty() -> {
                view.onValidateToTimeError("To time can't be empty")
            }
            else -> {
                view.onValidateToTimeSuccess()
            }
        }
    }

    fun validateForm(data: BookingData, formValid : Boolean) {
        when {
            data.isValid()&&formValid -> {
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

    private fun checkDay(value: String): String {
        val date = formatter.parse(value)
        Log.d("date", SimpleDateFormat("EEEE", Locale.US).format(date))
        // return day of week (Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)
        return SimpleDateFormat("EEEE", Locale.US).format(date)
    }

    private fun getTimePoint(startTime: String, endTime: String): Array<Timepoint> {
        val startTimeArray = startTime.split(":")
        val endTimeArray = endTime.split(":")
        var timesEnable = arrayOf<Timepoint>()
        for (i in startTimeArray[0].toInt()..endTimeArray[0].toInt()) {
            //hour start = hour end
            if ( startTimeArray[0].toInt() == endTimeArray[0].toInt()) {
                for (j in startTimeArray[1].toInt()..endTimeArray[1].toInt()) {
                    timesEnable += Timepoint(i, j)
                }
            }
            //hour start != hour end
            else{
                when {
                    //first hour
                    i == startTimeArray[0].toInt() -> {
                        for (j in startTimeArray[1].toInt()..59) {
                            timesEnable += Timepoint(i, j)
                        }
                    }
                    //last hour
                    i === endTimeArray[0].toInt() -> {
                        for (j in 0..endTimeArray[1].toInt()) {
                            timesEnable += Timepoint(i, j)
                        }
                    }
                    //between hour start and end
                    else -> {
                        for (j in 0..59) {
                            timesEnable += Timepoint(i, j)
                        }
                    }
                }
            }
        }
        Log.d("toTime", timesEnable.toString())
        return timesEnable
    }



}