package com.odds.oddsbooking.presentations.booking.form

import android.content.Context
import androidx.core.util.PatternsCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.odds.oddsbooking.R
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.di.DataModule
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.services.booking.BookingAPIFactory
import com.odds.oddsbooking.utils.NameUtilities.getNameFormatter
import java.text.SimpleDateFormat
import java.util.*

class BookingFormViewModel : ViewModel() {
    private val _setFromTimesDropDown by lazy { MutableLiveData<Array<String>>() }
    val setFromTimesDropDown: LiveData<Array<String>> get() = _setFromTimesDropDown

    private val _setToTimesDropDown by lazy { MutableLiveData<Array<String>>() }
    val setToTimesDropDown: LiveData<Array<String>> get() = _setToTimesDropDown

    //region onValidatesError/Success

    //region onValidateNameError/Success
    private val _onValidateNameError by lazy { MutableLiveData<Int>() }
    val onValidateNameError: LiveData<Int> get() = _onValidateNameError

    private val _onValidateNameSuccess by lazy {MutableLiveData<Unit>()}
    val onValidateNameSuccess: LiveData<Unit> get() = _onValidateNameSuccess

    private val _onNameAutoFormat by lazy { MutableLiveData<String>() }
    val onNameAutoFormat: LiveData<String> get() = _onNameAutoFormat
    //endregion

    //region onValidateEmailError/Success
    private val _onValidateEmailError by lazy { MutableLiveData<Int>() }
    val onValidateEmailError : LiveData<Int> get() = _onValidateEmailError

    private val _onValidateEmailSuccess by lazy { MutableLiveData<Unit>() }
    val onValidateEmailSuccess: LiveData<Unit> get() = _onValidateEmailSuccess

    private val _onValidatePhoneNumberError by lazy {MutableLiveData<Int>()}
    val onValidatePhoneNumberError : LiveData<Int> get() = _onValidatePhoneNumberError

    private val _onValidatePhoneNumberSuccess by lazy {MutableLiveData<Unit>()}
    val onValidatePhoneNumberSuccess : LiveData<Unit> get() = _onValidatePhoneNumberSuccess

    //endregion

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
}





