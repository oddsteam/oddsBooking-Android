package com.odds.oddsbooking.presentations.splash_screen

import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

//TODO: write new test
@RunWith(MockitoJUnitRunner::class)
class SplashPresenterTest {
    @Mock
    lateinit var view: SplashView
    private var presenter: SplashPresenter = SplashPresenter(
        Dispatchers.Unconfined, 0
    )



    @Before
    fun setup() {
//        presenter.attachView(view)
    }

    @Test
    fun `when open SplashScreen should call goToBookingForm`() {
        presenter.splashing()
        verify(view).goToBookingForm()
    }

}