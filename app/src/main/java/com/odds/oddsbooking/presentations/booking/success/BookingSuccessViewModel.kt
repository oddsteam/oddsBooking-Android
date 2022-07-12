package com.odds.oddsbooking.presentations.booking.success

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookingSuccessViewModel: ViewModel() {
    private val _onReturnToForm by lazy { MutableLiveData<Unit>() }
    val onReturnToForm: LiveData<Unit> get() = _onReturnToForm

    fun onReturnToForm() {
        _onReturnToForm.value = Unit
    }
}