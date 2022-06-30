package com.odds.oddsbooking.presentations.booking.preview

import android.util.Log
import com.odds.oddsbooking.models.Booking
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.services.booking.BookingAPI
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class BookingPreviewPresenter constructor(private val api: BookingAPI) {
    private val scope = MainScope()
    private lateinit var view: BookingPreviewView
    private  var bookingData: BookingData = BookingData(
        "","","","","","","","",""
    )
    private var bookingInfo: Booking = Booking(
        "","","","","","","",false
    )

    fun attachView(view: BookingPreviewView) {
        this.view = view
    }

    fun createBooking() {
        view.showProgressBar()
        scope.launch {
            try {
                val response = api.createBooking(bookingInfo)
                Log.d("res", response.body().toString());
                if (response.isSuccessful) {
                    view.goToSuccessPage(bookingData)
                } else view.showToastMessage("${response.errorBody()?.string()}")
            } catch (e: Exception) {
                Log.d("res", "error : /$e")
                view.showToastMessage("error : /$e")
            }
        }
    }


    fun backToBookingFormPage() {
            view.backToBookingFormPage()
    }

    fun getBookingInfo(bookingInfo: BookingData) {
        bookingData = bookingInfo
        this.bookingData = BookingData(
            bookingData.fullName,
            bookingData.email,
            bookingData.phoneNumber,
            bookingData.room,
            bookingData.reason,
            bookingData.fromDate,
            bookingData.fromTime,
            bookingData.toDate,
            bookingData.toTime
        )
        this.bookingInfo = Booking(
            bookingData.fullName,
            bookingData.email,
            bookingData.phoneNumber,
            bookingData.room,
            bookingData.reason,
            "${dateTimeGeneralFormat(bookingData.fromDate)}T${bookingData.fromTime}",
            "${dateTimeGeneralFormat(bookingData.toDate)}T${bookingData.toTime}",
            false
        )

        view.setAllEditTextFromBookingData(bookingData)
    }

    private fun dateTimeGeneralFormat(dateTime: String): String {
        val (year, month, day) = dateTime.split("/").toTypedArray()
        return "${year}-${month}-${day}"
    }
}