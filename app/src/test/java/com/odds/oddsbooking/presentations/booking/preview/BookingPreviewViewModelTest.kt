package com.odds.oddsbooking.presentations.booking.preview

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
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

    private lateinit var viewModel: BookingPreviewViewModel

    @Before
    fun setUp() {
        viewModel = BookingPreviewViewModel(context)
        viewModel.backToBookingFormPage.observeForever(backToBookingFormPage)
    }

    @Test
    fun `when preview back to BookingFormPage`() {
        //Given

        //When
        viewModel.backToBookingFormPage()
        //Then
        verify(backToBookingFormPage).onChanged(Unit)
    }
}