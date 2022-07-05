package com.odds.oddsbooking.splash_screen

import com.odds.oddsbooking.presentations.booking.form.BookingFormPresenter
import com.odds.oddsbooking.presentations.booking.form.BookingFormView
import com.odds.oddsbooking.presentations.splash_screen.SplashPresenter
import kotlinx.coroutines.Dispatchers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class SplashPresenterTest {
    @Mock
    lateinit var view: SplashPresenter.SplashView
    private var presenter: SplashPresenter = SplashPresenter(
        Dispatchers.Unconfined, 0
    )

    @Before
    fun setup() {
        presenter.attachView(view)
    }

    @Test
    fun `onCreate Splash screen should nav to BookingForm`() {
        presenter.splashing()
        verify(view).goToBookingForm()
    }

}