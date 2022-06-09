package com.odds.oddsbooking.booking_form

import android.util.Log
import com.odds.oddsbooking.services.booking.BookingAPI
import kotlinx.coroutines.*
import java.lang.Exception

class BookingPreviewPresenter constructor(private val api: BookingAPI) {
    private val scope = MainScope()
    private lateinit var view: BookingPreviewView

    //TODO create interface view file
    interface BookingPreviewView {
        fun showProgressBar()
        fun goToSuccessPage()
        fun backToBookingFormPage()
        fun showToastMessage(errorMessage: String)
    }

    fun attachView(view: BookingPreviewView) {
        this.view = view
    }

    fun createBooking(booking: Booking) {
        view.showProgressBar()
        scope.launch {
            try {
                val response = api.createBooking(booking)
                if (response.isSuccessful) {
                    view.goToSuccessPage()
                }
                else view.showToastMessage("${response.errorBody()?.string()}")
            } catch (e: Exception) {
                Log.d("res", "error : /$e")
                view.showToastMessage("error : /$e")
            }
        }
    }

    fun backToBookingFormPage() {
        scope.launch {
            view.backToBookingFormPage()
        }
    }
}