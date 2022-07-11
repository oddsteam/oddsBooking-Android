package com.odds.oddsbooking.presentations.splash_screen

import androidx.lifecycle.Observer
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock

class SplashScreenViewModelTest {
    private val showAnimationObserver : Observer<Unit> = mock()
    private val navigateToBookingFormObserver : Observer<Unit> = mock()

    private lateinit var viewModel: SplashScreenViewModel


    @Before
    fun setUp() {
        viewModel = SplashScreenViewModel()
    }

    //TODO: fix test: tomorrow
    @Test
    fun `when open SplashScreen should call goToBookingForm`() {
        viewModel.splashing()
        Mockito.verify(showAnimationObserver).onChanged(Unit)
    }
}