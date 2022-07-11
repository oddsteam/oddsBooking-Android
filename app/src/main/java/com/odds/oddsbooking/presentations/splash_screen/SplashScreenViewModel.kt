package com.odds.oddsbooking.presentations.splash_screen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenViewModel : ViewModel() {
    private val _showAnimation by lazy { MutableLiveData<Unit>() }
    val showAnimation : LiveData<Unit> get() = _showAnimation

    private val _navigateToBookingForm by lazy { MutableLiveData<Unit>() }
    val navigateToBookingForm : LiveData<Unit> get() = _navigateToBookingForm

    private val duration : Long = 1200

    fun splashing(){
//        view.showAnimation()
        _showAnimation.value = Unit

        viewModelScope.launch {
            delay(duration)
//            view.goToBookingForm()
            _navigateToBookingForm.value = Unit
        }
    }

}