package com.odds.oddsbooking.presentations.booking.form

import androidx.core.util.PatternsCompat
import com.odds.oddsbooking.R
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.models.DateInTimePickerType
import com.odds.oddsbooking.utils.DateUtilities.Companion.checkDay
import com.odds.oddsbooking.utils.DateUtilities.Companion.isSaturday
import com.odds.oddsbooking.utils.DateUtilities.Companion.isSunday
import com.odds.oddsbooking.utils.DateUtilities.Companion.isWeekend
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
        System.currentTimeMillis() + TWO_WEEKS,
        null,
        ""
    )

    private var fromDate = ""

    private var fromTimeTimeSlot: Array<String> = arrayOf()
    private var toTimeTimeSlot: Array<String> = arrayOf()

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
                bookingData.fullName = fullName
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
            !PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() -> {
                view.onValidateEmailError(R.string.email_format_err)
                true
            }
            else -> {
                view.onValidateEmailSuccess()
                bookingData.email = email
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
            !Regex("^0[9862][0-9]{8}\$").matches(phoneNumber) -> {
                view.onValidatePhoneNumberError(R.string.phone_number_format_err)
                true
            }
            else -> {
                view.onValidatePhoneNumberSuccess()
                bookingData.phoneNumber = phoneNumber
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
                bookingData.room = room
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
                bookingData.reason = reason
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
                //TODO: refactor code more readable
                if (isWeekend(fromDate)) {
//                    fromTimeTimeSlot = getTimeSlot("09:00", "20:00")
                    setFromTimeTimeSlot("09:00", "20:00")
//                    view.onValidateFromDateSuccess(fromTimeTimeSlot)
                }
                else {
//                    fromTimeTimeSlot = getTimeSlot("18:00", "22:00")
                    setFromTimeTimeSlot("18:00", "22:00")
//                    view.onValidateFromDateSuccess(fromTimeTimeSlot)
                }

                view.onValidateFromDateSuccess(fromTimeTimeSlot)

                view.setFromTimeDropdown(fromTimeTimeSlot)
                view.clearValueFromTimeDropdown()
                view.clearValueToTimeDropdown()
                view.setEnableFromTime()
                view.setDisableToDate()
                view.setDisableToTime()
                fromDateErrorFlag = false
                bookingData.fromDate = fromDate
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
                val toTime = "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"

                if (fromDate == toDate) {
                    if (isWeekend(fromDate)) {
//                        toTimeTimeSlot = getTimeSlot(toTime, "21:00")
                        setToTimeTimeSlot(toTime, "21:00")
                        view.onValidateFromTimeSuccess(toTimeTimeSlot)
                    }
                    //on weekday
                    else {
//                        toTimeTimeSlot = getTimeSlot(toTime, "23:00")
                        setToTimeTimeSlot(toTime, "23:00")
                        view.onValidateFromTimeSuccess(toTimeTimeSlot)
                    }
                } else {
                    val dayOfWeek = checkDay(fromDate)
                    if (arrayListOf("Saturday", "Sunday").contains(dayOfWeek)) {
//                        toTimeTimeSlot = getTimeSlot("09:30", "21:00")
                        setToTimeTimeSlot("09:30", "21:00")
                        view.onValidateFromTimeSuccess(toTimeTimeSlot)
                    }
                }
                view.setToTimeDropDown(toTimeTimeSlot)
                view.clearValueToTimeDropdown()
                view.setEnableToDate()
                view.setEnableToTime()
                fromTimeErrorFlag = false
                bookingData.fromTime = fromTime
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
                bookingData.toDate = toDate
                toDateErrorFlag = false
            }
            else -> {
                val fromTimeArray = fromTime.split(":")
                val toTime =
                    "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"
                //same day
                if (fromDate == toDate) {
                    //on weekend
                    if (isWeekend(fromDate)) {
//                        toTimeTimeSlot = getTimeSlot(toTime, "21:00")
                        setToTimeTimeSlot(toTime, "21:00")
                        view.onValidateToDateSuccess(toTimeTimeSlot)
                    }
                    //on weekday
                    else {
//                        toTimeTimeSlot = getTimeSlot(toTime, "23:00")
                        setToTimeTimeSlot(toTime, "23:00")
                        view.onValidateToDateSuccess(toTimeTimeSlot)
                    }
                }
                //other day
                else {
                    val dayOfWeek = checkDay(fromDate)
                    //on weekend
                    if (arrayListOf("Saturday", "Sunday").contains(dayOfWeek)) {
//                        toTimeTimeSlot = getTimeSlot("09:30", "21:00")
                        setToTimeTimeSlot("09:30", "21:00")
                        view.onValidateToDateSuccess(toTimeTimeSlot)
                    }
                }
                view.setToTimeDropDown(toTimeTimeSlot)
                view.clearValueToTimeDropdown()
                view.setEnableToTime()
                bookingData.toDate = toDate
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
                bookingData.toTime = toTime
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
            System.currentTimeMillis() + TWO_WEEKS,
            null,
            fromDate
        )
        view.onDatePickerDialogFormDate(dateInTimePickerDialog)
    }

    fun onToDateClick(toDate: String, fromDate: String) {
        //TODO: handle invalid format
        val date = formatter.parse(fromDate)

        val minDate: Long = date.time
        var maxDate: Long = date.time

        if (isSaturday(fromDate)) {
            maxDate = date.time + ONE_DAY
        }
        dateInTimePickerDialog =
            DateInTimePicker(
                datePickerType = DateInTimePickerType.TO_DATE,
                minDate,
                maxDate,
                toDate
            )
        //reset when click in toDate datePicker
        view.onDatePickerDialogToDate(dateInTimePickerDialog)
    }
    //endregion

    fun autoFormatName(name: String) {
        view.onNameAutoFormat(getNameFormatter(name))
    }

    private fun getNameFormatter(name: String): String {
        val nameFormatter = name.lowercase().trim().split("\\s+".toRegex()).toMutableList()
        for (index in nameFormatter.indices) {
            nameFormatter[index] = nameFormatter[index].replaceFirstChar { it.uppercaseChar() }
        }
        return nameFormatter.joinToString(" ")
    }

    private fun getDateFormatter(year: Int, month: Int, day: Int): String {
        return String.format("%d/%02d/%02d", year, month + 1, day)
    }

    //region setTimesDropDown
    fun setFromTimesDropDown() {
        view.setFromTimeDropdown(fromTimeTimeSlot)
    }

    fun setToTimesDropDown() {
        view.setToTimeDropDown(toTimeTimeSlot)
    }
    //endregion

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

    //region set/get TimeSlots
    fun setFromTimeTimeSlot(startTime: String, endTime: String) {
        fromTimeTimeSlot = getTimeSlot(startTime, endTime)
    }

    fun setToTimeTimeSlot(startTime: String, endTime: String) {
        toTimeTimeSlot = getTimeSlot(startTime, endTime)
    }

    fun getTimeSlot(startTime: String, endTime: String): Array<String> {
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
        return timeSlot
    }
    //endregion

    fun onPreviewButtonClicked() {
        view.onNavigateToPreview(bookingData)
    }

    companion object {
        const val ONE_DAY = 24 * 60 * 60 * 1000
        const val TWO_WEEKS = 14 * ONE_DAY
    }
}