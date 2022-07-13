package com.odds.oddsbooking.presentations.booking.preview

import android.util.Log
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.di.DataModule
import com.odds.oddsbooking.models.BookingRequest
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.services.booking.BookingAPI
import com.odds.oddsbooking.utils.DateUtilities.dateTimeGeneralFormat
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

//mvvm
class BookingPreviewPresenter constructor(
    private val dispatcher: CoroutineDispatcher,
    private val api: BookingAPI,
    //TODO: create api build fun
    private val bookingRepository: BookingRepository = DataModule.createBookingRepository(api)
) {
    private val scope = CoroutineScope(Job() + dispatcher)
    private lateinit var view: BookingPreviewView
    private var bookingData: BookingData = BookingData()

    private var bookingInfo: BookingRequest = BookingRequest()

    fun attachView(view: BookingPreviewView) {
        this.view = view
    }

    fun createBooking() {
        scope.launch {
            bookingRepository.createBooking(bookingInfo)

                .onStart {
                    view.showProgressBar()
                }
                .catch {
                    view.showToastMessage("${it.message}")
                }
//                .onEach {
//                    //ของมาทีละชิ้น เราจะทำอะไร *ต้อง collect ก่อน
//                }
                .map {

                }

                .collect {
                    view.goToSuccessPage(bookingData)
                }
//            try {
//                val response = api.createBooking(bookingInfo)
//                if (response.isSuccessful) {
//                    view.goToSuccessPage(bookingData)
//                } else view.showToastMessage("${response.errorBody()?.string()}")
//            } catch (e: Exception) {
//                view.showToastMessage("error : /$e")
//            }
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