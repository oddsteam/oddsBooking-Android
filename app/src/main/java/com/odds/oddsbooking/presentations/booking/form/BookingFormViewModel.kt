package com.odds.oddsbooking.presentations.booking.form

import android.content.Context
import android.util.Log
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.odds.oddsbooking.R
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.di.DataModule
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.models.DateInTimePickerType
import com.odds.oddsbooking.services.booking.BookingAPIFactory
import com.odds.oddsbooking.utils.DateUtilities
import com.odds.oddsbooking.utils.NameUtilities.getNameFormatter
import java.text.SimpleDateFormat
import java.util.*
import javax.sql.StatementEvent

class BookingFormViewModel : ViewModel() {
    private val _setFromTimesDropDown by lazy { MutableLiveData<Array<String>>() }
    val setFromTimesDropDown: LiveData<Array<String>> get() = _setFromTimesDropDown

    private val _setToTimesDropDown by lazy { MutableLiveData<Array<String>>() }
    val setToTimesDropDown: LiveData<Array<String>> get() = _setToTimesDropDown

    //region onValidatesError/Success

    //region onValidateNameError/Success
    private val _onValidateNameError by lazy { MutableLiveData<Int>() }
    val onValidateNameError: LiveData<Int> get() = _onValidateNameError

    private val _onValidateNameSuccess by lazy { MutableLiveData<Unit>() }
    val onValidateNameSuccess: LiveData<Unit> get() = _onValidateNameSuccess

    private val _onNameAutoFormat by lazy { MutableLiveData<String>() }
    val onNameAutoFormat: LiveData<String> get() = _onNameAutoFormat
    //endregion

    //region onValidateEmailError/Success
    private val _onValidateEmailError by lazy { MutableLiveData<Int>() }
    val onValidateEmailError: LiveData<Int> get() = _onValidateEmailError

    private val _onValidateEmailSuccess by lazy { MutableLiveData<Unit>() }
    val onValidateEmailSuccess: LiveData<Unit> get() = _onValidateEmailSuccess
    //endregion

    //region onValidatePhoneNumberError/Success
    private val _onValidatePhoneNumberError by lazy { MutableLiveData<Int>() }
    val onValidatePhoneNumberError: LiveData<Int> get() = _onValidatePhoneNumberError

    private val _onValidatePhoneNumberSuccess by lazy { MutableLiveData<Unit>() }
    val onValidatePhoneNumberSuccess: LiveData<Unit> get() = _onValidatePhoneNumberSuccess
    //endregion

    //region onValidateRoomError/Success
    private val _onValidateRoomError by lazy { MutableLiveData<Int>() }
    val onValidateRoomError: LiveData<Int> get() = _onValidateRoomError

    private val _onValidateRoomSuccess by lazy { MutableLiveData<Unit>() }
    val onValidateRoomSuccess: LiveData<Unit> get() = _onValidateRoomSuccess
    //endregion

    //region onValidateReasonError/Success
    private val _onValidateReasonError by lazy { MutableLiveData<Int>() }
    val onValidateReasonError: LiveData<Int> get() = _onValidateReasonError

    private val _onValidateReasonSuccess by lazy { MutableLiveData<Unit>() }
    val onValidateReasonSuccess: LiveData<Unit> get() = _onValidateReasonSuccess
    //endregion

    //region onValidateFromDateError/Success
    private val _onValidateFromDateError by lazy { MutableLiveData<Int>() }
    val onValidateFromDateError: LiveData<Int> get() = _onValidateFromDateError

    private val _onValidateFromDateSuccess by lazy { MutableLiveData<Array<String>>() }
    val onValidateFromDateSuccess: LiveData<Array<String>> get() = _onValidateFromDateSuccess
    //endregion

    //region onValidateFromTimeError/Success
    private val _onValidateFromTimeError by lazy { MutableLiveData<Int>() }
    val onValidateFromTimeError: LiveData<Int> get() = _onValidateFromTimeError

    private val _onValidateFromTimeSuccess by lazy { MutableLiveData<Array<String>>() }
    val onValidateFromTimeSuccess: LiveData<Array<String>> get() = _onValidateFromTimeSuccess
    //endregion

    //region onValidateToDateError/Success
    private val _onValidateToDateError by lazy { MutableLiveData<Int>() }
    val onValidateToDateError: LiveData<Int> get() = _onValidateToDateError

    private val _onValidateToDateSuccess by lazy { MutableLiveData<Array<String>>() }
    val onValidateToDateSuccess: LiveData<Array<String>> get() = _onValidateToDateSuccess
    //endregion

    //endregion

    private val _setFromTimeDropdown by lazy { MutableLiveData<Array<String>>() }
    val setFromTimeDropdown: LiveData<Array<String>> get() = _setFromTimeDropdown

    private val _setToTimeDropDown by lazy { MutableLiveData<Array<String>>() }
    val setToTimeDropDown: LiveData<Array<String>> get() = _setToTimeDropDown

    private val _clearValueFromTimeDropdown by lazy { MutableLiveData<Unit>() }
    val clearValueFromTimeDropdown: LiveData<Unit> get() = _clearValueFromTimeDropdown

    private val _clearValueToTimeDropdown by lazy { MutableLiveData<Unit>() }
    val clearValueToTimeDropdown: LiveData<Unit> get() = _clearValueToTimeDropdown

    private val _setEnableFromTime by lazy { MutableLiveData<Unit>() }
    val setEnableFromTime: LiveData<Unit> get() = _setEnableFromTime

    private val _setEnableToDate by lazy { MutableLiveData<Unit>() }
    val setEnableToDate: LiveData<Unit> get() = _setEnableToDate

    private val _setEnableToTime by lazy { MutableLiveData<Unit>() }
    val setEnableToTime: LiveData<Unit> get() = _setEnableToTime

    private val _setDisableToDate by lazy { MutableLiveData<Unit>() }
    val setDisableToDate: LiveData<Unit> get() = _setDisableToDate

    private val _setDisableToTime by lazy { MutableLiveData<Unit>() }
    val setDisableToTime: LiveData<Unit> get() = _setDisableToTime

    private val _enablePreviewButton by lazy { MutableLiveData<Unit>() }
    val enablePreviewButton: LiveData<Unit> get() = _enablePreviewButton

    private val _disablePreviewButton by lazy { MutableLiveData<Unit>() }
    val disablePreviewButton: LiveData<Unit> get() = _disablePreviewButton

    private val _onDatePickerDialogFormDate by lazy { MutableLiveData<DateInTimePicker>() }
    val onDatePickerDialogFormDate: LiveData<DateInTimePicker> get() = _onDatePickerDialogFormDate

    private val _onDatePickerDialogToDate by lazy { MutableLiveData<DateInTimePicker>() }
    val onDatePickerDialogToDate: LiveData<DateInTimePicker> get() = _onDatePickerDialogToDate

    private val _setDisableFromDateEditText by lazy { MutableLiveData<Unit>() }
    val setDisableFromDateEditText: LiveData<Unit> get() = _setDisableFromDateEditText

    private val _setDisableToDateEditText by lazy { MutableLiveData<Unit>() }
    val setDisableToDateEditText: LiveData<Unit> get() = _setDisableToDateEditText

    private val _setTextFromDate by lazy { MutableLiveData<String>() }
    val setTextFromDate: LiveData<String> get() = _setTextFromDate

    private val _setTextToDate by lazy { MutableLiveData<String>() }
    val setTextToDate: LiveData<String> get() = _setTextToDate

    private var fromTimeTimeSlot: Array<String> = arrayOf()
    private var toTimeTimeSlot: Array<String> = arrayOf()
    private var formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
    private var bookingData: BookingData = BookingData()
    private var fromDate = ""
    private var dateInTimePickerDialog = DateInTimePicker(
        datePickerType = DateInTimePickerType.FROM_DATE,
        System.currentTimeMillis() + BookingFormPresenter.TWO_WEEKS,
        null,
        ""
    )

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

    fun setFromTimesDropDown() {
        _setFromTimesDropDown.value = fromTimeTimeSlot
    }

    fun setToTimesDropDown() {
        _setToTimesDropDown.value = toTimeTimeSlot
    }

    fun validateFullName(fullName: String) {
        fullNameErrorFlag = when {
            fullName.isEmpty() -> {
                _onValidateNameError.value = R.string.full_name_empty_err
                true
            }
            else -> {
                _onValidateNameSuccess.value = Unit
                bookingData.fullName = fullName
                false
            }
        }
        validateForm()
    }

    fun validateEmail(email: String) {
        emailErrorFlag = when {
            email.isEmpty() -> {
                _onValidateEmailError.value = R.string.email_empty_err
                true
            }
            !PatternsCompat.EMAIL_ADDRESS.matcher(email).matches() -> {
                _onValidateEmailError.value = R.string.email_format_err
                true
            }
            else -> {
                _onValidateEmailSuccess.value = Unit
                bookingData.email = email
                false
            }
        }
        validateForm()
    }


    fun validateFromDate(fromDate: String) {
        this.fromDate = fromDate
        when {
            fromDate.isEmpty() -> {
                _onValidateFromDateError.value = R.string.from_date_empty_err
                fromDateErrorFlag = true
            }
            else -> {
                if (DateUtilities.isWeekend(fromDate)) {
                    setFromTimeTimeSlot(startTime = "09:00", endTime = "20:00")
                } else {
                    setFromTimeTimeSlot(startTime = "18:00", endTime = "22:00")
                }

                if(fromTimeTimeSlot.isNotEmpty()){
                    Log.d("formTimeTimeSlot", "${fromTimeTimeSlot.size}")
                }

                _onValidateFromDateSuccess.value = fromTimeTimeSlot
                _setFromTimeDropdown.value = fromTimeTimeSlot
                _clearValueFromTimeDropdown.value = Unit
                _clearValueToTimeDropdown.value = Unit
                _setEnableFromTime.value = Unit
                _setDisableToDate.value = Unit
                _setDisableToTime.value = Unit

                fromDateErrorFlag = false
                bookingData.fromDate = fromDate
            }
        }
        validateForm()
    }


    fun validateToDate(toDate: String, fromDate: String, fromTime: String) {
        when {
            toDate.isEmpty() -> {
                _onValidateToDateError.value = R.string.to_date_empty_err
                toDateErrorFlag = true
            }
            fromTime.isEmpty() -> {
                bookingData.toDate = toDate
                toDateErrorFlag = false
            }
            else -> {
                setUpToTimeTimeSlot(fromTime, fromDate, toDate)

                _onValidateToDateSuccess.value = toTimeTimeSlot

                _setToTimeDropDown.value = toTimeTimeSlot
                _clearValueToTimeDropdown.value = Unit
                _setEnableToTime.value = Unit
                bookingData.toDate = toDate
                toDateErrorFlag = false
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
                _enablePreviewButton.value = Unit
            }
            else -> {
                _disablePreviewButton.value = Unit
            }
        }
    }

    //region set/get TimeSlots
    fun setFromTimeTimeSlot(startTime: String, endTime: String) {
        fromTimeTimeSlot = DateUtilities.getTimeSlot(startTime, endTime)
    }

    fun setToTimeTimeSlot(startTime: String, endTime: String) {
        toTimeTimeSlot = DateUtilities.getTimeSlot(startTime, endTime)
    }
    //endregion

    fun autoFormatName(name: String) {
        _onNameAutoFormat.value = getNameFormatter(name)
    }

    fun validatePhoneNumber(phoneNumber: String) {
        phoneNumberErrorFlag = when {
            phoneNumber.isEmpty() -> {
                _onValidatePhoneNumberError.value = (R.string.phone_number_empty_err)
                true
            }
            !Regex("^0[9862][0-9]{8}\$").matches(phoneNumber) -> {
                _onValidatePhoneNumberError.value = (R.string.phone_number_format_err)
                true
            }
            else -> {
                _onValidatePhoneNumberSuccess.value = Unit
                bookingData.phoneNumber = phoneNumber
                false
            }
        }
        validateForm()
    }

    fun validateRoom(room: String) {
        roomErrorFlag = when {
            room.isEmpty() -> {
                _onValidateRoomError.value = (R.string.room_empty_err)
                true
            }
            else -> {
                _onValidateRoomSuccess.value = Unit
                bookingData.room = room
                false
            }
        }
        validateForm()
    }

    fun validateReason(reason: String) {
        reasonErrorFlag = when {
            reason.isEmpty() -> {
                _onValidateReasonError.value = (R.string.reason_empty_err)
                true
            }
            else -> {
                _onValidateReasonSuccess.value = Unit
                bookingData.reason = reason
                false
            }
        }
        validateForm()
    }

    fun validateFromTime(fromTime: String, fromDate: String, toDate: String) {
        when {
            fromTime.isEmpty() -> {
                _onValidateFromTimeError.value = R.string.time_empty_err
                fromDateErrorFlag = true
            }
            else -> {
                if (DateUtilities.isSameDate(fromDate, toDate)) {
                    val fromTimeArray = fromTime.split(":")
                    val startToTime = "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"

                    if (DateUtilities.isWeekend(fromDate)) {
                        setToTimeTimeSlot(startTime = startToTime, endTime =  "21:00")
                    }
                    else {
                        setToTimeTimeSlot(startTime = startToTime, endTime =  "23:00")
                    }
                } else {
                    if (DateUtilities.isWeekend(fromDate)) {
                        setToTimeTimeSlot(startTime = "09:00", endTime =  "21:00")
                    }
                }

                _onValidateFromTimeSuccess.value = toTimeTimeSlot

                _setToTimeDropDown.value = toTimeTimeSlot
                _clearValueToTimeDropdown.value = Unit
                _setEnableToDate.value = Unit
                _setEnableToTime.value = Unit
                fromTimeErrorFlag = false
                bookingData.fromTime = fromTime
            }
        }
        validateForm()
    }

    //region onDatePickersClick
    fun onFromDateClick(fromDate: String) {
        dateInTimePickerDialog = DateInTimePicker(
            DateInTimePickerType.FROM_DATE,
            System.currentTimeMillis() + BookingFormPresenter.TWO_WEEKS,
            null,
            fromDate
        )
        _onDatePickerDialogFormDate.value = dateInTimePickerDialog
    }

    fun onToDateClick(toDate: String, fromDate: String) {
        val date = formatter.parse(fromDate)

        val minDate: Long = date.time
        var maxDate: Long = date.time

        if (DateUtilities.isSaturday(fromDate)) {
            maxDate = date.time + BookingFormPresenter.ONE_DAY
        }

        dateInTimePickerDialog =
            DateInTimePicker(
                datePickerType = DateInTimePickerType.TO_DATE,
                minDate,
                maxDate,
                toDate
            )
        //reset when click in toDate datePicker
        _onDatePickerDialogToDate.value = dateInTimePickerDialog
    }

    fun onDatePickerCancel() {
        if (dateInTimePickerDialog.datePickerType == DateInTimePickerType.FROM_DATE) {
            _setDisableFromDateEditText.value = Unit
        } else {
            _setDisableToDateEditText.value = Unit
        }

    }

    fun onDatePickerDismiss() {
        if (dateInTimePickerDialog.datePickerType == DateInTimePickerType.FROM_DATE) {
            _setDisableFromDateEditText.value = Unit
        } else {
            _setDisableToDateEditText.value = Unit
        }
    }

    fun onDatePickerConfirm(year: Int, month: Int, day: Int) {
        val date = DateUtilities.getDateFormatter(year, month, day)
        if (dateInTimePickerDialog.datePickerType == DateInTimePickerType.FROM_DATE) {
            _setDisableFromDateEditText.value = Unit
            _setTextFromDate.value = date
        } else {
            _setDisableToDateEditText.value = Unit
            _setTextToDate.value = date
        }
    }

    private fun setUpToTimeTimeSlot(
        fromTime: String,
        fromDate: String,
        toDate: String
    ) {
        if (DateUtilities.isSameDate(fromDate, toDate)) {
            val fromTimeArray = fromTime.split(":")
            val toTime = "${fromTimeArray[0].toInt() + 1}:${fromTimeArray[1].toInt()}"

            if (DateUtilities.isWeekend(fromDate)) {
                setToTimeTimeSlot(toTime, "21:00")
            } else {
                setToTimeTimeSlot(toTime, "23:00")
            }
        } else {
            if (DateUtilities.isWeekend(fromDate)) {
                setToTimeTimeSlot("09:00", "21:00")
            }
        }
    }

}





