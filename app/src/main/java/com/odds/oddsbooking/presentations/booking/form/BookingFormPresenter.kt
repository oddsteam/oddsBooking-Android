package com.odds.oddsbooking.presentations.booking.form

import androidx.core.util.PatternsCompat
import com.odds.oddsbooking.R
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.models.DateInTimePickerType
import com.odds.oddsbooking.utils.DateUtilities.getDateFormatter
import com.odds.oddsbooking.utils.DateUtilities.getTimeSlot
import com.odds.oddsbooking.utils.DateUtilities.isSameDate
import com.odds.oddsbooking.utils.DateUtilities.isSaturday
import com.odds.oddsbooking.utils.DateUtilities.isWeekend
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
                if (isWeekend(fromDate)) {
                    setFromTimeTimeSlot(startTime =  "09:00", endTime =  "20:00")
                }
                else {
                    setFromTimeTimeSlot(startTime = "18:00", endTime = "22:00")
                }

                view.onValidateFromDateSuccess(fromTimeTimeSlot)

                view.setFromTimeDropDown(fromTimeTimeSlot)
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
                if (isSameDate(fromDate, toDate)) {
                    val fromTimeArray = fromTime.split(":")
                    val startToTime = "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"

                    if (isWeekend(fromDate)) {
                        setToTimeTimeSlot(startTime = startToTime, endTime =  "21:00")
                    }
                    else {
                        setToTimeTimeSlot(startTime = startToTime, endTime =  "23:00")
                    }
                } else {
                    if (isWeekend(fromDate)) {
                        setToTimeTimeSlot(startTime = "09:00", endTime =  "21:00")
                    }
                }

                view.onValidateFromTimeSuccess(toTimeTimeSlot)

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
                setUpToTimeTimeSlot(fromTime, fromDate, toDate)

                view.onValidateToDateSuccess(toTimeTimeSlot)

                view.setToTimeDropDown(toTimeTimeSlot)
                view.clearValueToTimeDropdown()
                view.setEnableToTime()
                bookingData.toDate = toDate
                toDateErrorFlag = false
            }
        }
        validateForm()
    }

    private fun setUpToTimeTimeSlot(
        fromTime: String,
        fromDate: String,
        toDate: String
    ) {
        if (isSameDate(fromDate, toDate)) {
            val fromTimeArray = fromTime.split(":")
            val toTime = "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"

            if (isWeekend(fromDate)) {
                setToTimeTimeSlot(toTime, "21:00")
            } else {
                setToTimeTimeSlot(toTime, "23:00")
            }
        } else {
            if (isWeekend(fromDate)) {
                setToTimeTimeSlot("09:00", "21:00")
            }
        }
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
        val date = formatter.parse(fromDate)

        val minDate: Long = date.time
        var maxDate: Long = date.time

        if (isSaturday(fromDate)) {
            maxDate = date.time + ONE_DAY
        }
        print("minDate : " + minDate + "\n")
        print("maxDate : " + maxDate + "\n")
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

//    private fun getDateFormatter(year: Int, month: Int, day: Int): String {
//        return String.format("%d/%02d/%02d", year, month + 1, day)
//    }

    //region setTimesDropDown
    fun setFromTimesDropDown() {
        view.setFromTimeDropDown(fromTimeTimeSlot)
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
    //endregion

    fun onPreviewButtonClicked() {
        view.onNavigateToPreview(bookingData)
    }

    companion object {
        const val ONE_DAY = 24 * 60 * 60 * 1000
        const val TWO_WEEKS = 14 * ONE_DAY
    }
}