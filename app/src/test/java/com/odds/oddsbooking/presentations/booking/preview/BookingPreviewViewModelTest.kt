package com.odds.oddsbooking.presentations.booking.preview

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.presentations.MainCoroutineScopeRule
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.mock
import org.mockito.kotlin.verify

@RunWith(JUnit4::class)
class BookingPreviewViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val backToBookingFormPage: Observer<Unit> = mock()
    private val context: Context = mock()
    private val setAllEditTextFromBookingData: Observer<BookingData> = mock()

    private lateinit var viewModel: BookingPreviewViewModel

    @Before
    fun setUp() {
        viewModel = BookingPreviewViewModel(context)
        viewModel.backToBookingFormPage.observeForever(backToBookingFormPage)
        viewModel.setAllEditTextFromBookingData.observeForever(setAllEditTextFromBookingData)
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
}