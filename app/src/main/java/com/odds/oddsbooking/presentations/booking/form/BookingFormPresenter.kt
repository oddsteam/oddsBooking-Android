package com.odds.oddsbooking.presentations.booking.form

import android.util.Log
import android.util.Patterns
import com.odds.oddsbooking.interfaces.BookingFormView
import com.wdullaer.materialdatetimepicker.time.Timepoint
import java.text.SimpleDateFormat
import java.util.*


class BookingFormPresenter {
    private lateinit var view: BookingFormView
    private var formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)

    private var fullNameErrorFlag = true
    private var emailErrorFlag = true
    private var phoneNumberErrorFlag = true
    private var roomErrorFlag = true
    private var reasonErrorFlag = true
    private var fromDateErrorFlag = true
    private var fromTimeErrorFlag = true
    private var toDateErrorFlag = true
    private var toTimeErrorFlag = true

    fun attachView(view: BookingFormView) {
        this.view = view
    }

    fun validateFullName(fullName: String) {
        fullNameErrorFlag = when {
            fullName.isEmpty() -> {
                view.onValidateNameError("fullName can't be empty")
                true
            }
            else -> {
                view.onValidateNameSuccess()
                false
            }
        }
    }

    fun validateEmail(email: String) {
        emailErrorFlag = when {
            email.isEmpty() -> {
                view.onValidateEmailError("Email can't be empty")
                true
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.onValidateEmailError("Format email error")
                true
            }
            else -> {
                view.onValidateEmailSuccess()
                false
            }
        }
    }

    fun validatePhoneNumber(phoneNumber: String) {
        phoneNumberErrorFlag = when {
            phoneNumber.isEmpty() -> {
                view.onValidatePhoneNumberError("Phone number can't be empty")
                true
            }
            !Regex("^0[9,8,6,2][0-9]{8}\$").matches(phoneNumber) -> {
                view.onValidatePhoneNumberError("Format phone number error")
                true
            }
            else -> {
                view.onValidatePhoneNumberSuccess()
                false
            }
        }
    }

    fun validateRoom(room: String) {
        roomErrorFlag = when {
            room.isEmpty() -> {
                view.onValidateRoomError("Please enter room")
                true
            }
            else -> {
                view.onValidateRoomSuccess()
                false
            }
        }
    }

    fun validateReason(reason: String) {
        reasonErrorFlag = when {
            reason.isEmpty() -> {
                view.onValidateReasonError("Reason can't be empty")
                true
            }
            else -> {
                view.onValidateReasonSuccess()
                false
            }
        }
    }

    fun validateFromDate(fromDate: String) {
        when {
            fromDate.isEmpty() -> {
                view.onValidateFromDateError("From date can't be empty")
                fromDateErrorFlag = true
            }
            else -> {
                val date = formatter.parse(fromDate)
                //on week end
                if (checkDay(fromDate) == "Saturday") {
                    val minDate: Long = date.time
                    val maxDate: Long = date.time + (24 * 60 * 60 * 1000) // can booking Sunday
                    view.onValidateFromDateSuccess(getTimeSlot("09:00", "20:00"), minDate, maxDate)
                }else if (checkDay(fromDate) == "Sunday"){
                    val minDate: Long = date.time
                    val maxDate: Long = date.time
                    view.onValidateFromDateSuccess(getTimeSlot("09:00", "20:00"), minDate, maxDate)
                }
                //on week day
                else {
                    val minDate: Long = date.time
                    val maxDate: Long = date.time
                    view.onValidateFromDateSuccess(getTimeSlot("18:00", "22:00"), minDate, maxDate)
                }
                fromDateErrorFlag = false
            }
        }
    }

    fun validateFromTime(fromTime: String, fromDate: String, toDate: String) {
        var toTime = "00:00"
        when {
            fromTime.isEmpty() -> {
                view.onValidateFromTimeError("From time can't be empty")
                fromDateErrorFlag = true
            }
            else -> {
                val fromTimeArray = fromTime.split(":")
                toTime =
                    "${fromTimeArray[0].toInt()+1}:${fromTimeArray[1].toInt()}"

                //same day
                if (fromDate == toDate) {
                    //on weekend
                    if (checkDay(fromDate) == "Sunday" || checkDay(fromDate) == "Saturday") {
                        view.onValidateFromTimeSuccess(getTimeSlot(toTime, "21:00"))
                    }
                    //on weekday
                    else {
                        view.onValidateFromTimeSuccess(getTimeSlot(toTime, "23:00"))
                    }
                }
                //other day
                else {
                    val dayOfWeek = checkDay(fromDate)
                    //on weekend
                    if (arrayListOf<String>("Saturday", "Sunday").contains(dayOfWeek)) {
                        view.onValidateFromTimeSuccess(getTimeSlot("09:30", "21:00"))
                    }
                    //on weekday
                    else {
                        view.onValidateFromTimeSuccess(getTimeSlot("18:30", "23:00"))
                    }
                }
                fromTimeErrorFlag = false
            }
        }
    }

    fun validateToDate(toDate: String, fromDate: String, fromTime: String) {
        var toTime = "00:00"
        when {
            toDate.isEmpty() -> {
                view.onValidateToDateError("To date can't be empty")
                toDateErrorFlag = true
            }
            fromTime.isEmpty() -> {}
            else -> {
                val fromTimeArray = fromTime.split(":")
                toTime =
                    "${fromTimeArray[0].toInt()+1}:${fromTimeArray[1].toInt()}"

                //same day
                if (fromDate == toDate) {
                    //on weekend
                    if (checkDay(fromDate) == "Sunday" || checkDay(fromDate) == "Saturday") {
                        view.onValidateToDateSuccess(getTimeSlot(toTime, "21:00"))
                    }
                    //on weekday
                    else {
                        view.onValidateToDateSuccess(getTimeSlot(toTime, "23:00"))
                    }
                }
                //other day
                else {
                    val dayOfWeek = checkDay(fromDate)
                    //on weekend
                    if (arrayListOf<String>("Saturday", "Sunday").contains(dayOfWeek)) {
                        view.onValidateToDateSuccess(getTimeSlot("09:30", "21:00"))
                    }
                    //on weekday
                    else {
                        view.onValidateToDateSuccess(getTimeSlot("18:30", "23:00"))
                    }
                }
                toDateErrorFlag = false
            }
        }
    }

    fun validateToTime(toTime: String) {
        toTimeErrorFlag = when {
            toTime.isEmpty() -> {
                view.onValidateToTimeError("To time can't be empty")
                true
            }
            else -> {
                view.onValidateToTimeSuccess()
                false
            }
        }
    }

    fun validateForm() {
        when {
            !fullNameErrorFlag
                    && !emailErrorFlag
                    && !phoneNumberErrorFlag
                    && !roomErrorFlag
                    && !reasonErrorFlag
                    && !fromDateErrorFlag
                    && !fromTimeErrorFlag
                    && !toDateErrorFlag
                    && !toTimeErrorFlag -> {
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
        val date = formatter.parse(value)!!
        Log.d("date", SimpleDateFormat("EEEE", Locale.US).format(date))
        // return day of week (Sunday, Monday, Tuesday, Wednesday, Thursday, Friday, Saturday)
        return SimpleDateFormat("EEEE", Locale.US).format(date)
    }

    private fun getTimeSlot(startTime: String, endTime: String): Array<String> {
        var timeSlot = arrayOf<String>()
        val startTimeArray = startTime.split(":")
        val endTimeArray = endTime.split(":")
        val startHr = startTimeArray[0].toInt()
        val startMin = startTimeArray[1].toInt()
        val endHr = endTimeArray[0].toInt()
        val endMin = endTimeArray[1].toInt()
        if(startHr == endHr){
            if (startMin == 0) timeSlot += "$startHr:00"
            if (startMin == 30) timeSlot += "$startHr:30"
        }else{
            for (i in startHr..endHr) {
                if (i == startHr) {
                    if (startMin == 0) timeSlot += "$i:00"
                    timeSlot += "$i:30"
                } else if (i != endHr) {
                    timeSlot += "$i:00"
                } else {
                    timeSlot += "$i:00"
                    if (endMin == 30) timeSlot += "$i:30"
                }
            }
        }
        Log.d("TimeSlot", timeSlot.toString())
        return timeSlot
    }


}