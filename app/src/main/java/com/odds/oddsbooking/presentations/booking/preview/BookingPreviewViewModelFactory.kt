package com.odds.oddsbooking.presentations.booking.preview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.di.DataModule
import com.odds.oddsbooking.services.booking.BookingAPI

class BookingPreviewViewModelFactory(
    private val api: BookingAPI,
    private val bookingRepository: BookingRepository = DataModule.createBookingRepository(api)
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        BookingPreviewViewModel(api, bookingRepository) as T
}