package com.odds.oddsbooking.presentations.booking.preview

interface BookingPreviewView {
    fun showProgressBar()
    fun goToSuccessPage()
    fun backToBookingFormPage()
    fun showToastMessage(errorMessage: String)
}