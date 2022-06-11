package com.odds.oddsbooking.interfaces

interface BookingPreviewView {
    fun showProgressBar()
    fun goToSuccessPage()
    fun backToBookingFormPage()
    fun showToastMessage(errorMessage: String)
}