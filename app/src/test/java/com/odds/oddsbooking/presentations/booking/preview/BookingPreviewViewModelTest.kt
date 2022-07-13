package com.odds.oddsbooking.presentations.booking.preview

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

@RunWith(JUnit4::class)
class BookingPreviewViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val backToBookingFormPage: Observer<Unit> = mock()

    private lateinit var viewModel: BookingPreviewViewModel

    @Before
    fun setUp() {
        //viewModel = BookingPreviewViewModel()
        viewModel.backToBookingFormPage.observeForever(backToBookingFormPage)
    }

    @Test
    fun `preview`() {
        //Given
        viewModel.backToBookingFormPage
        //When
        coroutineScope.testScheduler.apply {
            advanceTimeBy(1200); runCurrent()
        }
        //Then
        Mockito.verify(backToBookingFormPage).onChanged(Unit)
    }
}