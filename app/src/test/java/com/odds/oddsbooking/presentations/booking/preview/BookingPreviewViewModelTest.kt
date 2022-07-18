package com.odds.oddsbooking.presentations.booking.preview

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.presentations.MainCoroutineScopeRule
import com.odds.oddsbooking.services.booking.BookingDetailResponse
import com.odds.oddsbooking.services.booking.BookingResponse
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.kotlin.*

@RunWith(JUnit4::class)
class BookingPreviewViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val backToBookingFormPage: Observer<Unit> = mock()
    private val setAllEditTextFromBookingDataObserver: Observer<BookingData> = mock()
    private val showProcessBarObserver: Observer<Unit> = mock()
    private val showToastMassageObserver: Observer<String> = mock()
    private val goToSuccessPageObserver: Observer<BookingData> = mock()

    private val bookingRepository : BookingRepository = mock()

    private lateinit var viewModel: BookingPreviewViewModel

    @Before
    fun setUp() {
        viewModel = BookingPreviewViewModel(bookingRepository)
        viewModel.backToBookingFormPage.observeForever(backToBookingFormPage)
        viewModel.setAllEditTextFromBookingData.observeForever(setAllEditTextFromBookingDataObserver)
        viewModel.showProgressBar.observeForever(showProcessBarObserver)
        viewModel.showToastMessage.observeForever(showToastMassageObserver)
        viewModel.goToSuccessPage.observeForever(goToSuccessPageObserver)

    }

    @Test
    fun `when preview back to BookingFormPage`() {
        //Given

        //When
        viewModel.backToBookingFormPage()
        //Then
        verify(backToBookingFormPage).onChanged(Unit)
    }

    @Test
    fun `when getBookingInfo should call setAllEditTextFromBookingData`() {
        //Given
        val bookingData = BookingData(
            "Mola Mola",
            "Mola@odds.team",
            "0889537322",
            "Neon",
            "Test Preview",
            "2022/07/05",
            "18:00",
            "2022/07/05",
            "20:00"
        )
        //When
        viewModel.getBookingInfo(bookingData)
        //Then
        verify(setAllEditTextFromBookingDataObserver).onChanged(bookingData)
    }

    @Test
    fun `when createBooking should call showProgressBar()`() {
        //Given
        //When
        viewModel.createBooking()
        //Then
        verify(showProcessBarObserver).onChanged(Unit)
    }

    @Test
    fun `when call createBooking and response error , api should response`() = runTest {
        //Given
        whenever(bookingRepository.createBooking(any())).doReturn(flow { throw Exception("response not success") })

        //When
        viewModel.createBooking()

        //Then
        verify(showToastMassageObserver).onChanged("response not success")
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
        viewModel.getBookingInfo(bookingData)

        whenever(bookingRepository.createBooking(any())).doReturn(flowOf(bookingRes))

        //When
        viewModel.createBooking()

        //Then
        verify(goToSuccessPageObserver).onChanged(bookingData)
    }
}