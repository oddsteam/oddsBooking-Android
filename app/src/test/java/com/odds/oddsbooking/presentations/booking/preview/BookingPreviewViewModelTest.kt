package com.odds.oddsbooking.presentations.booking.preview

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.BookingRequest
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
import org.mockito.Mockito
import org.mockito.kotlin.*

@RunWith(JUnit4::class)
class BookingPreviewViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val backToBookingFormPage: Observer<Unit> = mock()
    private val context: Context = mock()
    private val setAllEditTextFromBookingData: Observer<BookingData> = mock()
    private val showProcessBar: Observer<Unit> = mock()
    private val showToastMassage: Observer<String> = mock()
    private val goToSuccessPage: Observer<BookingData> = mock()
    private val bookingRepository : BookingRepository = mock()

    private lateinit var viewModel: BookingPreviewViewModel

    @Before
    fun setUp() {
        viewModel = BookingPreviewViewModel(context)
        viewModel.backToBookingFormPage.observeForever(backToBookingFormPage)
        viewModel.setAllEditTextFromBookingData.observeForever(setAllEditTextFromBookingData)
        viewModel.showProgressBar.observeForever(showProcessBar)
        viewModel.showToastMessage.observeForever(showToastMassage)
        viewModel.goToSuccessPage.observeForever(goToSuccessPage)

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
        verify(setAllEditTextFromBookingData).onChanged(bookingData)
    }

    @Test
    fun `when createBooking should call showProgressBar()`() {
        //Given
        //When
        viewModel.createBooking()
        //Then
        verify(showProcessBar).onChanged(Unit)
    }

    @Test
    fun `when call createBooking and response error , api should response`() = runTest {
        //Given

        //When
        viewModel.createBooking()
        //Then
        verify(showToastMassage).onChanged("response not success")
    }

//    @Test
//    fun `when call createBooking and response success, api should response`() = runTest {
//        //Given
//
//        //When
//
//        //Then
//
//    }
}