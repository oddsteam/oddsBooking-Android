package com.odds.oddsbooking.presentations.booking.preview

import com.odds.oddsbooking.models.BookingRequest
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.services.booking.BookingAPI
import com.odds.oddsbooking.utils.DateUtilities.dateTimeGeneralFormat
import kotlinx.coroutines.*

class BookingPreviewPresenter constructor(
    private val dispatcher: CoroutineDispatcher,
    private val api: BookingAPI
) {
    private val scope = CoroutineScope(Job() + dispatcher)
    private lateinit var view: BookingPreviewView
    private var bookingData: BookingData = BookingData()

    private var bookingInfo: BookingRequest = BookingRequest()

    fun attachView(view: BookingPreviewView) {
        this.view = view
    }

    fun createBooking() {
        view.showProgressBar()
        scope.launch {
            try {
                val response = api.createBooking(bookingInfo)
                if (response.isSuccessful) {
                    view.goToSuccessPage(bookingData)
                } else view.showToastMessage("${response.errorBody()?.string()}")
            } catch (e: Exception) {
                view.showToastMessage("error : /$e")
            }
        }
    }

    fun backToBookingFormPage() {
        view.backToBookingFormPage()
    }

    fun getBookingInfo(bookingInfo: BookingData?) {
        if(bookingInfo != null){
            bookingData = bookingInfo
            this.bookingInfo = BookingRequest(
                bookingData.fullName,
                bookingData.email,
                bookingData.phoneNumber,
                bookingData.room,
                bookingData.reason,
                "${dateTimeGeneralFormat(bookingData.fromDate, bookingData.fromTime)}",
                "${dateTimeGeneralFormat(bookingData.toDate, bookingData.toTime)}",
                false
            )

            view.setAllEditTextFromBookingData(bookingData)
        }
    }
}