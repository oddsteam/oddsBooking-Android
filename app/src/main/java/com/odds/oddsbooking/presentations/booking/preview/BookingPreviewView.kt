package com.odds.oddsbooking.presentations.booking.preview

import com.odds.oddsbooking.models.BookingData

interface BookingPreviewView {
    fun showProgressBar()
    fun goToSuccessPage(bookingData: BookingData)
    fun backToBookingFormPage()
    fun showToastMessage(errorMessage: String)
    fun setAllEditTextFromBookingData(bookingData: BookingData)
}