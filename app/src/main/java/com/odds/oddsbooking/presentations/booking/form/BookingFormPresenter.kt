package com.odds.oddsbooking.presentations.booking.form

import android.util.Log
import android.util.Patterns
import com.odds.oddsbooking.R
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.models.DateInTimePickerType
import java.text.SimpleDateFormat
import java.util.*


class BookingFormPresenter {
    private lateinit var view: BookingFormView
    private var formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
    private var bookingData: BookingData = BookingData()

    //region formVarsErrorFlag
    private var fullNameErrorFlag = true
    private var emailErrorFlag = true
    private var phoneNumberErrorFlag = true
    private var roomErrorFlag = true
    private var reasonErrorFlag = true
    private var fromDateErrorFlag = true
    private var fromTimeErrorFlag = true
    private var toDateErrorFlag = true
    private var toTimeErrorFlag = true
    //endregion

    private var dateInTimePickerDialog = DateInTimePicker(
        datePickerType = DateInTimePickerType.FROM_DATE,
        System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
        null,
        ""
    )

    private var fromDate = ""

    private var fromTimeTimeSlot: Array<String> = arrayOf<String>()
    private var toTimeTimeSlot: Array<String> = arrayOf<String>()

    fun attachView(view: BookingFormView) {
        this.view = view
    }

    //region validates
    fun validateFullName(fullName: String) {
        fullNameErrorFlag = when {
            fullName.isEmpty() -> {
                view.onValidateNameError(R.string.full_name_empty_err)
                true
            }
            else -> {
                view.onValidateNameSuccess()

                false
            }
        }
        validateForm()
    }

    fun validateEmail(email: String) {
        emailErrorFlag = when {
            email.isEmpty() -> {
                view.onValidateEmailError(R.string.email_empty_err)
                true
            }
            !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.onValidateEmailError(R.string.email_format_err)
                true
            }
            else -> {
                view.onValidateEmailSuccess()
                false
            }
        }
        validateForm()
    }

    fun validatePhoneNumber(phoneNumber: String) {
        phoneNumberErrorFlag = when {
            phoneNumber.isEmpty() -> {
                view.onValidatePhoneNumberError(R.string.phone_number_empty_err)
                true
            }
            !Regex("^0[9,8,6,2][0-9]{8}\$").matches(phoneNumber) -> {
                view.onValidatePhoneNumberError(R.string.phone_number_format_err)
                true
            }
            else -> {
                view.onValidatePhoneNumberSuccess()
                false
            }
        }
        validateForm()
    }

    fun validateRoom(room: String) {
        roomErrorFlag = when {
            room.isEmpty() -> {
                view.onValidateRoomError(R.string.room_empty_err)
                true
            }
            else -> {
                view.onValidateRoomSuccess()
                false
            }
        }
        validateForm()
    }

    fun validateReason(reason: String) {
        reasonErrorFlag = when {
            reason.isEmpty() -> {
                view.onValidateReasonError(R.string.reason_empty_err)
                true
            }
            else -> {
                view.onValidateReasonSuccess()
                false
            }
        }
        validateForm()
    }

    fun validateFromDate(fromDate: String) {
        this.fromDate = fromDate
        when {
            fromDate.isEmpty() -> {
                view.onValidateFromDateError(R.string.from_date_empty_err)
                fromDateErrorFlag = true
            }
            else -> {
                //on week end
                if (checkDay(fromDate) == "Saturday") {
                    fromTimeTimeSlot = getTimeSlot("09:00", "20:00")
                    view.onValidateFromDateSuccess(getTimeSlot("09:00", "20:00"))
                } else if (checkDay(fromDate) == "Sunday") {
                    fromTimeTimeSlot = getTimeSlot("09:00", "20:00")
                    view.onValidateFromDateSuccess(getTimeSlot("09:00", "20:00"))
                }
                //on week day
                else {
                    fromTimeTimeSlot = getTimeSlot("18:00", "22:00")
                    view.onValidateFromDateSuccess(getTimeSlot("18:00", "22:00"))
                }

                view.setFromTimeDropdown(fromTimeTimeSlot)
                view.clearValueFromTimeDropdown()
                view.clearValueToTimeDropdown()
                view.setEnableFromTime()
                view.setDisableToDate()
                view.setDisableToTime()
                fromDateErrorFlag = false
            }
        }
        validateForm()
    }

    fun validateFromTime(fromTime: String, fromDate: String, toDate: String) {
        when {
            fromTime.isEmpty() -> {
                view.onValidateFromTimeError(R.string.time_empty_err)
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
                        toTimeTimeSlot = getTimeSlot(toTime, "21:00")
                        view.onValidateFromTimeSuccess(getTimeSlot(toTime, "21:00"))

                    }
                    //on weekday
                    else {
                        toTimeTimeSlot = getTimeSlot(toTime, "23:00")
                        view.onValidateFromTimeSuccess(getTimeSlot(toTime, "23:00"))
                    }
                }
                //other day
                else {
                    val dayOfWeek = checkDay(fromDate)
                    //on weekend
                    if (arrayListOf("Saturday", "Sunday").contains(dayOfWeek)) {
                        toTimeTimeSlot = getTimeSlot("09:30", "21:00")
                        view.onValidateFromTimeSuccess(getTimeSlot("09:30", "21:00"))
                    }
                    //on weekday
                    else {
                        toTimeTimeSlot = getTimeSlot("18:30", "23:00")
                        view.onValidateFromTimeSuccess(getTimeSlot("18:30", "23:00"))
                    }
                }
                view.setToTimeDropDown(toTimeTimeSlot)
                view.clearValueToTimeDropdown()
                view.setEnableToDate()
                view.setEnableToTime()
                fromTimeErrorFlag = false
            }
        }
        validateForm()
    }

    fun validateToDate(toDate: String, fromDate: String, fromTime: String) {
        when {
            toDate.isEmpty() -> {
                view.onValidateToDateError(R.string.to_date_empty_err)
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
                        toTimeTimeSlot = getTimeSlot(toTime, "21:00")
                        view.onValidateToDateSuccess(getTimeSlot(toTime, "21:00"))

                    }
                    //on weekday
                    else {
                        toTimeTimeSlot = getTimeSlot(toTime, "23:00")
                        view.onValidateToDateSuccess(getTimeSlot(toTime, "23:00"))
                    }
                }
                //other day
                else {
                    val dayOfWeek = checkDay(fromDate)
                    //on weekend
                    if (arrayListOf("Saturday", "Sunday").contains(dayOfWeek)) {
                        toTimeTimeSlot = getTimeSlot("09:30", "21:00")
                        view.onValidateToDateSuccess(getTimeSlot("09:30", "21:00"))
                    }
                    //on weekday
                    else {
                        toTimeTimeSlot = getTimeSlot("18:30", "23:00")
                        view.onValidateToDateSuccess(getTimeSlot("18:30", "23:00"))
                    }
                }
                view.setToTimeDropDown(toTimeTimeSlot)
                view.clearValueToTimeDropdown()
                view.setEnableToTime()
                toDateErrorFlag = false
            }
        }
        validateForm()
    }

    fun validateToTime(toTime: String) {
        toTimeErrorFlag = when {
            toTime.isEmpty() -> {
                view.onValidateToTimeError(R.string.time_empty_err)
                true
            }
            else -> {
                view.onValidateToTimeSuccess()
                false
            }
        }
        validateForm()
    }

    private fun validateForm() {
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
    //endregion

    //region onDatePickersClick
    fun onFromDateClick(fromDate: String) {
        dateInTimePickerDialog = DateInTimePicker(
            DateInTimePickerType.FROM_DATE,
            System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
            null,
            fromDate
        )
        view.onDatePickerDialogFormDate(dateInTimePickerDialog)
    }

    fun onToDateClick(toDate: String) {
        val date = formatter.parse(fromDate)
        //on week end
        if (checkDay(fromDate) == "Saturday") {
            val minDate: Long = date.time
            val maxDate: Long = date.time + (24 * 60 * 60 * 1000) // can booking Sunday
            dateInTimePickerDialog =
                DateInTimePicker(
                    datePickerType = DateInTimePickerType.TO_DATE,
                    minDate,
                    maxDate,
                    toDate
                )
        } else if (checkDay(fromDate) == "Sunday") {
            val minDate: Long = date.time
            val maxDate: Long = date.time
            dateInTimePickerDialog =
                DateInTimePicker(
                    datePickerType = DateInTimePickerType.TO_DATE,
                    minDate,
                    maxDate,
                    toDate
                )
        }
        //on week day
        else {
            val minDate: Long = date.time
            val maxDate: Long = date.time
            dateInTimePickerDialog =
                DateInTimePicker(
                    datePickerType = DateInTimePickerType.TO_DATE,
                    minDate,
                    maxDate,
                    toDate
                )
        }
        //reset when click in toDate datePicker
        view.onDatePickerDialogToDate(dateInTimePickerDialog)
    }
    //endregion

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

    private fun getDateFormatter(year: Int, month: Int, day: Int): String {
        return String.format("%d/%02d/%02d", year, month + 1, day)
    }

    fun setFromTimesDropDown() {
        view.setFromTimeDropdown(fromTimeTimeSlot)

    }

    fun setToTimesDropDown() {
        view.setToTimeDropDown(toTimeTimeSlot)
    }

    //region onDatePickers...
    fun onDatePickerCancel() {
        if (dateInTimePickerDialog.datePickerType == DateInTimePickerType.FROM_DATE) {
            view.setDisableFromDateEditText()
        } else {
            view.setDisableToDateEditText()
        }

    }

    fun onDatePickerDismiss() {
        if (dateInTimePickerDialog.datePickerType == DateInTimePickerType.FROM_DATE) {
            view.setDisableFromDateEditText()
        } else {
            view.setDisableToDateEditText()
        }
    }

    fun onDatePickerConfirm(year: Int, month: Int, day: Int) {
        val date = getDateFormatter(year, month, day)
        if (dateInTimePickerDialog.datePickerType == DateInTimePickerType.FROM_DATE) {
            view.setDisableFromDateEditText()
            view.setTextFromDate(date)
        } else {
            view.setDisableToDateEditText()
            view.setTextToDate(date)
        }
    }
    //endregion
}