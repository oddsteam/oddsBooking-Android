package com.odds.oddsbooking.presentations.booking.preview

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.di.DataModule
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.BookingRequest
import com.odds.oddsbooking.services.booking.BookingAPI
import com.odds.oddsbooking.services.booking.BookingAPIFactory
import com.odds.oddsbooking.utils.DateUtilities
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class BookingPreviewViewModel constructor(
    private val context: Context,
    private val bookingRepository: BookingRepository = DataModule.createBookingRepository(
        BookingAPIFactory.createBookingAPI(
            context
        ))
) : ViewModel() {
    private val _setAllEditTextFromBookingData by lazy { MutableLiveData<BookingData>() }
    val setAllEditTextFromBookingData : LiveData<BookingData> get() = _setAllEditTextFromBookingData

    private val _showProgressBar by lazy { MutableLiveData<Unit>() }
    val showProgressBar : LiveData<Unit> get() = _showProgressBar

    private val _goToSuccessPage by lazy { MutableLiveData<BookingData>() }
    val goToSuccessPage : LiveData<BookingData> get() = _goToSuccessPage

    private val _backToBookingFormPage by lazy { MutableLiveData<Unit>() }
    val backToBookingFormPage : LiveData<Unit> get() = _backToBookingFormPage

    private val _showToastMessage by lazy { MutableLiveData<String>() }
    val showToastMessage : LiveData<String> get() = _showToastMessage

    private var bookingData: BookingData = BookingData()

    private var bookingInfo: BookingRequest = BookingRequest()

    fun backToBookingFormPage() {
        _backToBookingFormPage.value = Unit
    }

    fun getBookingInfo(bookingInfo: BookingData?){
        if(bookingInfo != null){
            bookingData = bookingInfo
            this.bookingInfo = BookingRequest(
                bookingData.fullName,
                bookingData.email,
                bookingData.phoneNumber,
                bookingData.room,
                bookingData.reason,
                "${DateUtilities.dateTimeGeneralFormat(bookingData.fromDate, bookingData.fromTime)}",
                "${DateUtilities.dateTimeGeneralFormat(bookingData.toDate, bookingData.toTime)}",
                false
            )

            _setAllEditTextFromBookingData.value = bookingData
        }
    }

    fun createBooking(){
        viewModelScope.launch {
            bookingRepository.createBooking(bookingInfo)

                .onStart {
                    _showProgressBar.value = Unit
                }
                .catch {
                    _showToastMessage.value =  it.message
                }
//                .onEach {
//                    //ของมาทีละชิ้น เราจะทำอะไร *ต้อง collect ก่อน
//                }

                .collect {
                    _goToSuccessPage.value = bookingData
                }
        }
    }


}