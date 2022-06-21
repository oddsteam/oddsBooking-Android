package com.odds.oddsbooking.presentations.booking.form

import android.util.Log
import android.util.Patterns
import com.odds.oddsbooking.models.CalendarDate
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.models.DateInTimePickerType
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

    private var dateInTimePickerDialog = DateInTimePicker(
        type = DateInTimePickerType.FROM_DATE,
        System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
        null
    );
//    private var fromDateDialog =
//        DateInTimePicker(
//            type = DateInTimePickerType.FROM_DATE,
//            System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
//            null
//        )
//    private var toDateDialog =
//        DateInTimePicker(type = DateInTimePickerType.TO_DATE, 0, 0)

    private var calendar = Calendar.getInstance()
    private var calendarDate = CalendarDate(
        calendar,
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    private var fromDate = ""

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
        this.fromDate = fromDate
        when {
            fromDate.isEmpty() -> {
                view.onValidateFromDateError("From date can't be empty")
                fromDateErrorFlag = true
            }
            else -> {
//                //on week end
                if (checkDay(fromDate) == "Saturday") {

                    view.onValidateFromDateSuccess(getTimeSlot("09:00", "20:00"))

                    view.setEnableFromTime()
                    view.setDisableToDate()
                    view.setDisableToTime()
                } else if (checkDay(fromDate) == "Sunday") {
                    view.onValidateFromDateSuccess(getTimeSlot("09:00", "20:00"))

                    view.setEnableFromTime()
                    view.setDisableToDate()
                    view.setDisableToTime()
                }
                //on week day
                else {
                    view.onValidateFromDateSuccess(getTimeSlot("18:00", "22:00"))

                    view.setEnableFromTime()
                    view.setDisableToDate()
                    view.setDisableToTime()
                }
                fromDateErrorFlag = false
            }
        }
    }

    fun onFromDateClick() {
        dateInTimePickerDialog = DateInTimePicker(
            DateInTimePickerType.FROM_DATE,
            System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
            null
        )
        view.onDatePickerDialogFormDate(dateInTimePickerDialog)
    }

    fun onToDateClick() {
        val date = formatter.parse(fromDate)
        //on week end
        if (checkDay(fromDate) == "Saturday") {
            val minDate: Long = date.time
            val maxDate: Long = date.time + (24 * 60 * 60 * 1000) // can booking Sunday
            dateInTimePickerDialog =
                DateInTimePicker(type = DateInTimePickerType.TO_DATE, minDate, maxDate)
        } else if (checkDay(fromDate) == "Sunday") {
            val minDate: Long = date.time
            val maxDate: Long = date.time
            dateInTimePickerDialog =
                DateInTimePicker(type = DateInTimePickerType.TO_DATE, minDate, maxDate)
        }
        //on week day
        else {
            val minDate: Long = date.time
            val maxDate: Long = date.time
            dateInTimePickerDialog =
                DateInTimePicker(type = DateInTimePickerType.TO_DATE, minDate, maxDate)
        }
        //reset when click in toDate datePicker
        view.onDatePickerDialogToDate(dateInTimePickerDialog)
    }

    fun validateFromTime(fromTime: String, fromDate: String, toDate: String) {
        when {
            fromTime.isEmpty() -> {
                view.onValidateFromTimeError("Time can't be empty")
                fromDateErrorFlag = true
            }
            else -> {
                val fromTimeArray = fromTime.split(":")
                val toTime =
                    "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"

                //same day
                if (fromDate == toDate) {
                    //on weekend
                    if (checkDay(fromDate) == "Sunday" || checkDay(fromDate) == "Saturday") {
                        view.onValidateFromTimeSuccess(getTimeSlot(toTime, "21:00"))
                        view.setEnableToDate()
                        view.setEnableToTime()
                    }
                    //on weekday
                    else {
                        view.onValidateFromTimeSuccess(getTimeSlot(toTime, "23:00"))
                        view.setEnableToDate()
                        view.setEnableToTime()
                    }
                }
                //other day
                else {
                    val dayOfWeek = checkDay(fromDate)
                    //on weekend
                    if (arrayListOf("Saturday", "Sunday").contains(dayOfWeek)) {
                        view.onValidateFromTimeSuccess(getTimeSlot("09:30", "21:00"))
                        view.setEnableToDate()
                        view.setEnableToTime()
                    }
                    //on weekday
                    else {
                        view.onValidateFromTimeSuccess(getTimeSlot("18:30", "23:00"))
                        view.setEnableToDate()
                        view.setEnableToTime()
                    }
                }
                fromTimeErrorFlag = false
            }
        }
    }

    fun validateToDate(toDate: String, fromDate: String, fromTime: String) {
        when {
            toDate.isEmpty() -> {
                view.onValidateToDateError("To date can't be empty")
                toDateErrorFlag = true
            }
            fromTime.isEmpty() -> {
                toDateErrorFlag = false
            }
            else -> {
                val fromTimeArray = fromTime.split(":")
                val toTime =
                    "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"

                //same day
                if (fromDate == toDate) {
                    //on weekend
                    if (checkDay(fromDate) == "Sunday" || checkDay(fromDate) == "Saturday") {
                        view.onValidateToDateSuccess(getTimeSlot(toTime, "21:00"))
                        view.setEnableToTime()
                    }
                    //on weekday
                    else {
                        view.onValidateToDateSuccess(getTimeSlot(toTime, "23:00"))
                        view.setEnableToTime()
                    }
                }
                //other day
                else {
                    val dayOfWeek = checkDay(fromDate)
                    //on weekend
                    if (arrayListOf("Saturday", "Sunday").contains(dayOfWeek)) {
                        view.onValidateToDateSuccess(getTimeSlot("09:30", "21:00"))
                        view.setEnableToTime()
                    }
                    //on weekday
                    else {
                        view.onValidateToDateSuccess(getTimeSlot("18:30", "23:00"))
                        view.setEnableToTime()
                    }
                }
                toDateErrorFlag = false
            }
        }
    }

    fun validateToTime(toTime: String) {
        toTimeErrorFlag = when {
            toTime.isEmpty() -> {
                view.onValidateToTimeError("Time can't be empty")

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
        if (startHr == endHr) {
            if (startMin == 0) timeSlot += "$startHr:00"
            if (startMin == 30) timeSlot += "$startHr:30"
        } else {
            for (i in startHr..endHr) {
                if (i == startHr) {
                    if (startMin == 0) timeSlot += "$i:00"
                    timeSlot += "$i:30"
                } else if (i != endHr) {
                    timeSlot += "$i:00"
                    timeSlot += "$i:30"
                } else {
                    timeSlot += "$i:00"
                    if (endMin == 30) timeSlot += "$i:30"
                }
            }
        }
        Log.d("TimeSlot", timeSlot.toString())
        return timeSlot
    }

    fun getNameFormatter(name: String): String {
        val nameFormatter = name.lowercase().trim().split("\\s+".toRegex()).toMutableList()
        for (index in nameFormatter.indices) {
            nameFormatter[index] = nameFormatter[index].replaceFirstChar { it.uppercaseChar() }
        }
        return nameFormatter.joinToString(" ")
    }

    fun getDateFormatter(year: Int, month: Int, day: Int): String {
        return String.format("%d/%02d/%02d", year, month + 1, day)
    }
    //TODO: รวมเข้าไปใน DateIntimePicker (model)
    fun getCurrentCalendar(timePicked: String): CalendarDate {
        calendarDate = if (timePicked.isNotEmpty()) {
            val dates = timePicked.split("/")
            val years = dates[0].toInt()
            val months = dates[1].toInt() - 1
            val days = dates[2].toInt()
            CalendarDate(calendar, years, months, days)
        } else {
            val years = calendar.get(Calendar.YEAR)
            val months = calendar.get(Calendar.MONTH)
            val days = calendar.get(Calendar.DAY_OF_MONTH)
            CalendarDate(calendar, years, months, days)
        }

        return calendarDate
    }

    fun onDatePickerCancel() {
        if(dateInTimePickerDialog.type == DateInTimePickerType.FROM_DATE){
            view.setDisableFromDateEditText()
        }else{
            view.setDisableToDateEditText()
        }

    }

    fun onDatePickerDismiss() {
        if(dateInTimePickerDialog.type == DateInTimePickerType.FROM_DATE){
            view.setDisableFromDateEditText()
        }else{
            view.setDisableToDateEditText()
        }
    }

    fun onDatePickerConfirm() {
        if(dateInTimePickerDialog.type == DateInTimePickerType.FROM_DATE){
            view.setDisableFromDateEditText()
        }else{
            view.setDisableToDateEditText()
        }
    }
}