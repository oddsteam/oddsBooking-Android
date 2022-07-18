package com.odds.oddsbooking.presentations.booking.preview

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.odds.oddsbooking.di.DataModule
import com.odds.oddsbooking.services.booking.BookingAPIFactory

class BookingPreviewViewModelFactory(
    private val context: Context
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        val bookingAPI = BookingAPIFactory.createBookingAPI(context)
        val bookingRepository =  DataModule.createBookingRepository(bookingAPI)
        return BookingPreviewViewModel(bookingRepository) as T
    }
}