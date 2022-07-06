package com.odds.oddsbooking.presentations.booking.preview

import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.services.booking.BookingAPI
import com.odds.oddsbooking.services.booking.BookingDetailResponse
import com.odds.oddsbooking.services.booking.BookingResponse
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.*
import retrofit2.Response

class BookingPreviewPresenterTest {
    private val api: BookingAPI = mock()
    private val view: BookingPreviewView = mock()
    private lateinit var presenter: BookingPreviewPresenter

    @Before
    fun setUp() {
        presenter = BookingPreviewPresenter(
            kotlinx.coroutines.Dispatchers.Unconfined,
            api
        )
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
    fun `when getBookingInfo should call setAllEditTextFromBookingData`() {
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
    fun `when createBooking should call showProgressBar()`() {
        //Given
        //When
        presenter.createBooking()
        //Then
        verify(view).showProgressBar()
    }

    @Test
    fun `when call createBooking and response success, api should response`() = runTest {
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
        val bookingRes = BookingResponse(
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
        presenter.getBookingInfo(bookingData)

        val response = Response.success(200, bookingRes)
        whenever(api.createBooking(any())).doReturn(response)

        //When
        presenter.createBooking()

        //Then
        verify(view).goToSuccessPage(bookingData)
    }

    @Test
    fun `when call createBooking and response error , api should response`() = runTest {
        //Given
        val responseBody =  "error body".toResponseBody()
        val response = Response.error<BookingResponse>(401, responseBody)
        whenever(api.createBooking(any())).doReturn(response)

        //When
        presenter.createBooking()

        //Then
        verify(view).showToastMessage("error body")
    }

    @Test
    fun `when call createBooking and handshake not passed , api should response`() = runTest {
        //Given
        whenever(api.createBooking(any())).then{
            //refine words
            throw Exception("error")
        }
        //When
        presenter.createBooking()

        //Then
        verify(view).showToastMessage("error : /java.lang.Exception: error")
    }
}
