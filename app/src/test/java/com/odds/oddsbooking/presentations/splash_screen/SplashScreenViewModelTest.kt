package com.odds.oddsbooking.presentations.splash_screen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.odds.oddsbooking.presentations.MainCoroutineScopeRule
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runCurrent
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SplashScreenViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    private val showAnimationObserver : Observer<Unit> = mock()
    private val navigateToBookingFormObserver : Observer<Unit> = mock()

    private lateinit var viewModel: SplashScreenViewModel


    @Before
    fun setUp() {
        viewModel = SplashScreenViewModel()

        viewModel.showAnimation.observeForever(showAnimationObserver)
        viewModel.navigateToBookingForm.observeForever(navigateToBookingFormObserver)
    }

    @Test
    fun `when open SplashScreen should call goToBookingForm`() {
        //Given
        viewModel.splashing()

        //When
        coroutineScope.testScheduler.apply {
            advanceTimeBy(1200) ; runCurrent()
        }

        //Then
        Mockito.verify(showAnimationObserver).onChanged(Unit)
    }
}