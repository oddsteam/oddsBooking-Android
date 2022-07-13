package com.odds.oddsbooking.presentations.booking.form

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.odds.oddsbooking.R
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.di.DataModule
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.services.booking.BookingAPIFactory
import java.text.SimpleDateFormat
import java.util.*

class BookingFormViewModel : ViewModel() {
    private val _setFromTimesDropDown by lazy { MutableLiveData<Array<String>>() }
    val setFromTimesDropDown: LiveData<Array<String>> get() = _setFromTimesDropDown

    private val _setToTimesDropDown by lazy { MutableLiveData<Array<String>>() }
    val setToTimesDropDown: LiveData<Array<String>> get() = _setToTimesDropDown

    private val _validateFullName by lazy {MutableLiveData<String>()}
    val validateFullName: LiveData<String> get() = _validateFullName

    private val _onValidateNameError by lazy { MutableLiveData<String>() }
    val onValidateNameError: LiveData<String> get() = _onValidateNameError

    private val _onValidateNameSuccess by lazy {MutableLiveData<Unit>()}
    val onValidateNameSuccess: LiveData<Unit> get() = _onValidateNameSuccess

    private val _enablePreviewButton by lazy {MutableLiveData<Unit>()}
    val enablePreviewButton: LiveData<Unit> get() = _enablePreviewButton

    private val _disablePreviewButton by lazy {MutableLiveData<Unit>()}
    val disablePreviewButton:LiveData<Unit> get() = _disablePreviewButton

    private var fromTimeTimeSlot: Array<String> = arrayOf()
    private var toTimeTimeSlot: Array<String> = arrayOf()
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

    fun setFromTimesDropDown() {
        _setFromTimesDropDown.value = fromTimeTimeSlot
    }

    fun setToTimesDropDown(){
        _setToTimesDropDown.value = toTimeTimeSlot
    }

    fun validateFullName(fullName: String) {
        fullNameErrorFlag = when {
            fullName.isEmpty() -> {
                _onValidateNameError.value = (R.string.full_name_empty_err.toString())
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
}





