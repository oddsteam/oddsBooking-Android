package com.odds.oddsbooking.booking.preview

import android.content.Context
import com.odds.oddsbooking.presentations.booking.preview.BookingPreviewPresenter
import com.odds.oddsbooking.presentations.booking.preview.BookingPreviewView
import com.odds.oddsbooking.services.booking.BookingAPIFactory
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.mock
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookingPreviewPresenterTest {
    @Mock
    private var context: Context = mock(Context::class.java)
    lateinit var view: BookingPreviewView
    private var presenter = BookingPreviewPresenter(
        BookingAPIFactory.createBookingAPI(
            context
        )
    )

    @Before
    fun setUp() {
        view = mock(BookingPreviewView::class.java)
        presenter.attachView(view)
    }

    @Test
    fun `when click return button should call backToBookingFormPage`() {
        presenter.backToBookingFormPage()
        Mockito.verify(view, times(1)).backToBookingFormPage()
    }



}