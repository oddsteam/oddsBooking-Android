package com.odds.oddsbooking.booking.form

import com.odds.oddsbooking.R
import com.odds.oddsbooking.presentations.booking.form.BookingFormPresenter
import com.odds.oddsbooking.presentations.booking.form.BookingFormView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookingFormPresenterTest {
    @Mock
    lateinit var view: BookingFormView
    private var presenter: BookingFormPresenter = BookingFormPresenter()
    @Before
    fun setup(){
        presenter.attachView(view)
    }

    @Test
    fun `when input fullName correct should call onValidateNameError & DisablePreviewButton`() {
        val name = "Tar"
        presenter.validateFullName(name)
        Mockito.verify(view, times(1)).onValidateNameSuccess()
        Mockito.verify(view, times(0)).onValidateNameError(R.string.full_name_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input fullName incorrect should call onValidateNameError & DisablePreviewButton`() {
        val name = ""
        presenter.validateFullName(name)
        Mockito.verify(view, times(0)).onValidateNameSuccess()
        Mockito.verify(view, times(1)).onValidateNameError(R.string.full_name_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input email empty should call onValidateEmailError & DisablePreviewButton`() {
        val email = ""
        presenter.validateEmail(email)
        Mockito.verify(view, times(0)).onValidateEmailSuccess()
        Mockito.verify(view, times(1)).onValidateEmailError(R.string.email_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    //TODO: Email incorrect, correct

    @Test
    fun `when input phoneNumber empty should call onValidatePhoneNumberError & DisablePreviewButton`() {
        val phoneNumber = ""
        presenter.validatePhoneNumber(phoneNumber)
        Mockito.verify(view, times(0)).onValidatePhoneNumberSuccess()
        Mockito.verify(view, times(1)).onValidatePhoneNumberError(R.string.phone_number_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input phoneNumber incorrect should call onValidatePhoneNumberError & DisablePreviewButton`() {
        val phoneNumber = "0123456789"
        presenter.validatePhoneNumber(phoneNumber)
        Mockito.verify(view, times(0)).onValidatePhoneNumberSuccess()
        Mockito.verify(view, times(1)).onValidatePhoneNumberError(R.string.phone_number_format_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }
}