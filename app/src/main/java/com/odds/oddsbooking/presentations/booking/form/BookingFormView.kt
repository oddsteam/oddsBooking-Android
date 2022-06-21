package com.odds.oddsbooking.presentations.booking.form

import com.odds.oddsbooking.models.DateInTimePicker

interface BookingFormView{
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
    fun onValidateFromDateSuccess(timeSlot: Array<String>)
    fun onValidateFromTimeError(errMsg: String)
    fun onValidateFromTimeSuccess(timeSlot: Array<String>)
    fun onValidateToDateError(errMsg: String)
    fun onValidateToDateSuccess(timeSlot: Array<String>)
    fun onValidateToTimeError(errMsg: String)
    fun onValidateToTimeSuccess()
    fun onDatePickerDialogFormDate(fromDate: DateInTimePicker)
    fun onDatePickerDialogToDate(toDate: DateInTimePicker)
}

// TODO: rename
interface AA{
    fun setFromTimeEnable(isEnable: Boolean, backgroundColor: Int)
    fun setToDateEnable(isEnable: Boolean, backgroundColor: Int)
    fun setToTimeEnable(isEnable: Boolean, backgroundColor: Int)
}
