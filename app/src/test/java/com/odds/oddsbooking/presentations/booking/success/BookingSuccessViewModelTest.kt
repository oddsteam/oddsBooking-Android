package com.odds.oddsbooking.presentations.booking.success

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.odds.oddsbooking.presentations.MainCoroutineScopeRule
import com.odds.oddsbooking.presentations.splash_screen.SplashScreenViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito
import org.mockito.kotlin.mock

@RunWith(JUnit4::class)
class BookingSuccessViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val onReturnToForm : Observer<Unit> = mock()

    private lateinit var viewModel: BookingSuccessViewModel

    @Before
    fun setUp() {
        viewModel = BookingSuccessViewModel()
        viewModel.onReturnToForm.observeForever(onReturnToForm)
    }

    @Test
    fun `when input data success should call onReturnToForm`() {
        //Given
        viewModel.onReturnToForm()
        //When
        coroutineScope.testScheduler.apply {
            advanceTimeBy(1200) ; runCurrent()
        }
        //Then
        Mockito.verify(onReturnToForm).onChanged(Unit)
    }
}