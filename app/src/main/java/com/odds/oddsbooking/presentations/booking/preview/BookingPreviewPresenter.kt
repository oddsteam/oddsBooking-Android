package com.odds.oddsbooking.presentations.booking.preview

import android.util.Log
import com.odds.oddsbooking.models.Booking
import com.odds.oddsbooking.services.booking.BookingAPI
import kotlinx.coroutines.*
import java.lang.Exception

class BookingPreviewPresenter constructor(private val api: BookingAPI) {
    private val scope = MainScope()
    private lateinit var view: BookingPreviewView

    fun attachView(view: BookingPreviewView) {
        this.view = view
    }

    fun createBooking(booking: Booking) {
        view.showProgressBar()
        scope.launch {
            try {
                val response = api.createBooking(booking)
                Log.d("res",response.body().toString());
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