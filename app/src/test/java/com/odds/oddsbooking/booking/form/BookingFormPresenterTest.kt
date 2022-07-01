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
    fun `when input fullName correct should call onValidateNameSuccess & DisablePreviewButton`() {
        //Given
        val name = "Mola Mola"
        //When
        presenter.validateFullName(name)
        //Then
        Mockito.verify(view, times(1)).onValidateNameSuccess()
        Mockito.verify(view, times(0)).onValidateNameError(R.string.full_name_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input fullName incorrect should call onValidateNameError & DisablePreviewButton`() {
        //Given
        val name = ""
        //When
        presenter.validateFullName(name)
        //Then
        Mockito.verify(view, times(0)).onValidateNameSuccess()
        Mockito.verify(view, times(1)).onValidateNameError(R.string.full_name_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input email correct should call onValidateEmailSuccess & DisablePreviewButton`(){
        //Given
        val email = "molamola@gmail.com"
        //When
        presenter.validateEmail(email)
        //Then
        Mockito.verify(view, times(1)).onValidateEmailSuccess()
        Mockito.verify(view, times(0)).onValidateEmailError(R.string.email_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input email empty should call onValidateEmailError & DisablePreviewButton`() {
        //Given
        val email = ""
        //When
        presenter.validateEmail(email)
        //Then
        Mockito.verify(view, times(0)).onValidateEmailSuccess()
        Mockito.verify(view, times(1)).onValidateEmailError(R.string.email_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input email incorrect should call onValidateEmailError & DisablePreviewButton`() {
        //Given
        val email = "email"
        //When
        presenter.validateEmail(email)
        //Then
        Mockito.verify(view, times(0)).onValidateEmailSuccess()
        Mockito.verify(view, times(1)).onValidateEmailError(R.string.email_format_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input phoneNumber empty should call onValidatePhoneNumberError & DisablePreviewButton`() {
        //Given
        val phoneNumber = ""
        //When
        presenter.validatePhoneNumber(phoneNumber)
        //Then
        Mockito.verify(view, times(0)).onValidatePhoneNumberSuccess()
        Mockito.verify(view, times(1)).onValidatePhoneNumberError(R.string.phone_number_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input phoneNumber incorrect should call onValidatePhoneNumberError & DisablePreviewButton`() {
        //Given
        val phoneNumber = "0123456789"
        //When
        presenter.validatePhoneNumber(phoneNumber)
        //Then
        Mockito.verify(view, times(0)).onValidatePhoneNumberSuccess()
        Mockito.verify(view, times(1)).onValidatePhoneNumberError(R.string.phone_number_format_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input phoneNumber correct should call onValidatePhoneNumberSuccess & DisablePreviewButton`() {
        //Given
        val phoneNumber = "0823456789"
        //When
        presenter.validatePhoneNumber(phoneNumber)
        //Then
        Mockito.verify(view, times(1)).onValidatePhoneNumberSuccess()
        Mockito.verify(view, times(0)).onValidatePhoneNumberError(R.string.phone_number_format_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when do not select room should call onValidateRoomError & DisablePreviewButton`() {
        //Given
        val room = ""
        //When
        presenter.validateRoom(room)
        //Then
        Mockito.verify(view, times(0)).onValidateRoomSuccess()
        Mockito.verify(view, times(1)).onValidateRoomError(R.string.room_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when select room correct should call onValidateRoomSuccess & DisablePreviewButton`() {
        //Given
        val room = "Neon"
        //When
        presenter.validateRoom(room)
        //Then
        Mockito.verify(view, times(1)).onValidateRoomSuccess()
        Mockito.verify(view, times(0)).onValidateRoomError(R.string.room_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when do not input Reason empty should call onValidateReasonError & DisablePreviewButton`() {
        //Given
        val reason = ""
        //When
        presenter.validateReason(reason)
        //Then
        Mockito.verify(view, times(0)).onValidateReasonSuccess()
        Mockito.verify(view, times(1)).onValidateReasonError(R.string.reason_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input Reason correct should call onValidateReasonSuccess & DisablePreviewButton`() {
        //Given
        val reason = "study"
        //When
        presenter.validateReason(reason)
        //Then
        Mockito.verify(view, times(1)).onValidateReasonSuccess()
        Mockito.verify(view, times(0)).onValidateReasonError(R.string.reason_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when select fromDate correct should call onValidateFromDateSuccess & DisablePreviewButton`() {
        //Given
        val fromDate = "2022/07/22"
        val timeSlot = arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        //When
        presenter.validateFromDate(fromDate)
        //Then
        Mockito.verify(view, times(1)).onValidateFromDateSuccess(timeSlot)
        Mockito.verify(view, times(0)).onValidateFromDateError(R.string.from_date_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
        Mockito.verify(view, times(1)).setEnableFromTime()
        Mockito.verify(view, times(1)).setFromTimeDropdown(timeSlot)
        Mockito.verify(view, times(1)).clearValueFromTimeDropdown()
        Mockito.verify(view, times(1)).clearValueToTimeDropdown()
        Mockito.verify(view, times(1)).setDisableToDate()
        Mockito.verify(view, times(1)).setDisableToTime()
    }

    @Test
    fun `when do not select fromDate incorrect should call onValidateFromDateError & DisablePreviewButton`() {
        //Given
        val fromDate = ""
        val timeSlot = arrayOf("")
        //When
        presenter.validateFromDate(fromDate)
        //Then
        Mockito.verify(view, times(0)).onValidateFromDateSuccess(timeSlot)
        Mockito.verify(view, times(1)).onValidateFromDateError(R.string.from_date_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
        Mockito.verify(view, times(0)).setEnableFromTime()
        Mockito.verify(view, times(0)).setFromTimeDropdown(timeSlot)
        Mockito.verify(view, times(0)).clearValueFromTimeDropdown()
        Mockito.verify(view, times(0)).clearValueToTimeDropdown()
        Mockito.verify(view, times(0)).setDisableToDate()
        Mockito.verify(view, times(0)).setDisableToTime()
    }
}