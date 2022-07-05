package com.odds.oddsbooking.booking.preview

import android.content.Context
import com.odds.oddsbooking.models.Booking
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.presentations.booking.preview.BookingPreviewPresenter
import com.odds.oddsbooking.presentations.booking.preview.BookingPreviewView
import com.odds.oddsbooking.services.booking.BookingAPIFactory
import com.odds.oddsbooking.services.booking.BookingDetailResponse
import com.odds.oddsbooking.services.booking.BookingRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.stubbing.OngoingStubbing
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class BookingPreviewPresenterTest {
    @Mock
    private var context: Context = mock(Context::class.java)
    @Mock
    private var api = BookingAPIFactory.createBookingAPI(context)

    lateinit var view: BookingPreviewView
    private var presenter = BookingPreviewPresenter(
        Dispatchers.Unconfined,
        api
    )

    @Before
    fun setUp() {
        view = mock(BookingPreviewView::class.java)
        presenter.attachView(view)
    }

    @Test
    fun `when click return button should call backToBookingFormPage`() {
        //Given
        //When
        presenter.backToBookingFormPage()
        //Then
        verify(view).backToBookingFormPage()
    }

    @Test
    fun `when getBooking should call setAllEditTextFromBookingData`(){
        //Given
        val bookingData = BookingData(
            "Sittidet Pawutinan",
            "sittidet@odds.team",
            "0889537322",
            "Neon",
            "Test Preview",
            "2022/07/05",
            "18:00",
            "2022/07/05",
            "20:00"
        )
        //When
        presenter.getBookingInfo(bookingData)
        //Then
        verify(view).setAllEditTextFromBookingData(bookingData)
    }

    @Test
    fun `when createBooking should call showProgressBar()`(){
        //Given
        //When
        presenter.createBooking()
        //Then
        verify(view).showProgressBar()
    }

    @Test
    fun `when call createBooking , api should response`(){
        val bookingData = BookingData(
            "Sittidet Pawutinan",
            "sittidet@odds.team",
            "0889537322",
            "Neon",
            "Test Preview",
            "2022/07/05",
            "18:00",
            "2022/07/05",
            "20:00"
        )
        val booking = Booking(
            "Sittidet Pawutinan",
            "sittidet@odds.team",
            "0889537322",
            "Neon",
            "Test Preview",
            "2022-07-05T18:00",
            "2022-07-05T20:00",
            false
        )
        val bookingRes = BookingRes(
            status = 200,
            data = BookingDetailResponse(
                "1",
                "Sittidet Pawutinan",
                "sittidet@odds.team",
                "0889537322",
                "Neon",
                "Test Preview",
                "2022-07-05T18:00",
                "2022-07-05T20:00",
                status = false
            )
        )
    }
}
