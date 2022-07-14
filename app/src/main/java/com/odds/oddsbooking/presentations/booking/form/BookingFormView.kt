package com.odds.oddsbooking.presentations.booking.form

import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker

interface BookingFormView : IDatePicker {
    fun onNameAutoFormat(name: String)
    fun enablePreviewButton()
    fun disablePreviewButton()

    //region onValidates
    fun onValidateNameError(errMsg: Int)
    fun onValidateNameSuccess()
    fun onValidateEmailError(errMsg: Int)
    fun onValidateEmailSuccess()
    fun onValidatePhoneNumberError(errMsg: Int)
    fun onValidatePhoneNumberSuccess()
    fun onValidateRoomError(errMsg: Int)
    fun onValidateRoomSuccess()
    fun onValidateReasonError(errMsg: Int)
    fun onValidateReasonSuccess()
    fun onValidateFromDateError(errMsg: Int)
    fun onValidateFromDateSuccess(timeSlot: Array<String>)
    fun onValidateFromTimeError(errMsg: Int)
    fun onValidateFromTimeSuccess(timeSlot: Array<String>)
    fun onValidateToDateError(errMsg: Int)
    fun onValidateToDateSuccess(timeSlot: Array<String>)
    fun onValidateToTimeError(errMsg: Int)
    fun onValidateToTimeSuccess()

    //endregion
    fun onDatePickerDialogFormDate(fromDate: DateInTimePicker)
    fun onDatePickerDialogToDate(toDate: DateInTimePicker)

    fun setFromTimeDropDown(timeSlot: Array<String>)
    fun setToTimeDropDown(timeSlot: Array<String>)
    fun clearValueFromTimeDropdown()
    fun clearValueToTimeDropdown()

    fun onNavigateToPreview(bookingData: BookingData)
}

interface IDatePicker {
    fun setDisableFromDateEditText()
    fun setDisableToDateEditText()
    fun setEnableFromTime()
    fun setDisableFromTime()
    fun setEnableToDate()
    fun setDisableToDate()
    fun setEnableToTime()
    fun setDisableToTime()
    fun setTextFromDate(date: String)
    fun setTextToDate(date: String)
}
