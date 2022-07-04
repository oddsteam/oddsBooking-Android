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
    fun `when do not input reason empty should call onValidateReasonError & DisablePreviewButton`() {
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
    fun `when input reason correct should call onValidateReasonSuccess & DisablePreviewButton`() {
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

    @Test
    fun `when select fromTime correct should call onValidateFromTimeSuccess & DisablePreviewButton`() {
        //Given
        val fromTime = "13:00"
        val timeSlot = arrayOf("14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00")
        //When
        presenter.validateFromTime(fromTime,"2022/07/22","2022/07/22")
        //Then
        Mockito.verify(view, times(1)).onValidateFromTimeSuccess(timeSlot)
        Mockito.verify(view, times(0)).onValidateFromTimeError(R.string.time_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
        Mockito.verify(view, times(1)).setToTimeDropDown(timeSlot)
        Mockito.verify(view, times(1)).clearValueToTimeDropdown()
        Mockito.verify(view, times(1)).setEnableToDate()
        Mockito.verify(view, times(1)).setEnableToTime()
    }

    @Test
    fun `when do not select fromTime incorrect should call onValidateFromTimeError & DisablePreviewButton`() {
        //Given
        val fromTime = ""
        val timeSlot = arrayOf("")
        //When
        presenter.validateFromTime(fromTime,"2022/07/22","2022/07/22")
        //Then
        Mockito.verify(view, times(0)).onValidateFromTimeSuccess(timeSlot)
        Mockito.verify(view, times(1)).onValidateFromTimeError(R.string.time_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
        Mockito.verify(view, times(0)).setToTimeDropDown(timeSlot)
        Mockito.verify(view, times(0)).clearValueToTimeDropdown()
        Mockito.verify(view, times(0)).setEnableToDate()
        Mockito.verify(view, times(0)).setEnableToTime()
    }

    @Test
    fun `when select toDate correct should call onValidateToDateSuccess & DisablePreviewButton`() {
        //Given
        val fromDate = "2022/07/22"
        val toDate = "2022/07/22"
        val timeSlot = arrayOf("14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00")
        //When
        presenter.validateToDate(fromDate, toDate, "13:00")
        //Then
        Mockito.verify(view, times(1)).onValidateToDateSuccess(timeSlot)
        Mockito.verify(view, times(0)).onValidateToDateError(R.string.to_date_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
        Mockito.verify(view, times(1)).setEnableToTime()
        Mockito.verify(view, times(1)).clearValueToTimeDropdown()
    }

    @Test
    fun `when do not select toDate incorrect should call onValidateToDateError & DisablePreviewButton`() {
        //Given
        val timeSlot = arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        //When
        presenter.validateToDate("","2022/07/22", "13:00")
        //Then
        Mockito.verify(view, times(0)).onValidateToDateSuccess(timeSlot)
        Mockito.verify(view, times(1)).onValidateToDateError(R.string.to_date_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
        Mockito.verify(view, times(0)).setEnableToTime()
        Mockito.verify(view, times(0)).clearValueToTimeDropdown()
    }

    @Test
    fun `when select toTime correct should call onValidateToTimeSuccess & DisablePreviewButton`() {
        //Given
        val toTime = "17:00"
        //When
        presenter.validateToTime(toTime)
        //Then
        Mockito.verify(view, times(1)).onValidateToTimeSuccess()
        Mockito.verify(view, times(0)).onValidateToTimeError(R.string.time_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when do not select toTime incorrect should call onValidateToTimeError & DisablePreviewButton`() {
        //Given
        val toTime = ""
        //When
        presenter.validateToTime(toTime)
        //Then
        Mockito.verify(view, times(0)).onValidateToTimeSuccess()
        Mockito.verify(view, times(1)).onValidateToTimeError(R.string.time_empty_err)
        Mockito.verify(view, times(1)).disablePreviewButton()
    }

    @Test
    fun `when input all data complete correct should call onValidateSuccess & EnablePreviewButton`() {
        //Given
        val name = "Mola Mola"
        val email = "mola@odds.team"
        val phoneNumber = "0987654321"
        val room = "Neon"
        val reason = "study"
        val fromDate = "2022/07/22"
        val fromTime = "13:00"
        val toDate = "2022/07/22"
        val toTime = "17:00"
        val timeSlot = arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        val timeSlotCan = arrayOf("14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00")


        //When
        presenter.validateFullName(name)
        presenter.validateEmail(email)
        presenter.validatePhoneNumber(phoneNumber)
        presenter.validateRoom(room)
        presenter.validateReason(reason)
        presenter.validateFromDate(fromDate)
        presenter.validateFromTime(fromTime,fromDate,toDate)
        presenter.validateToDate(fromDate, toDate, fromTime)
        presenter.validateToTime(toTime)
        //Then
        Mockito.verify(view, times(1)).onValidateNameSuccess()
        Mockito.verify(view, times(1)).onValidateEmailSuccess()
        Mockito.verify(view, times(1)).onValidatePhoneNumberSuccess()
        Mockito.verify(view, times(1)).onValidateRoomSuccess()
        Mockito.verify(view, times(1)).onValidateReasonSuccess()
        Mockito.verify(view, times(1)).onValidateFromDateSuccess(timeSlot)
        Mockito.verify(view, times(1)).onValidateFromTimeSuccess(timeSlotCan)
        Mockito.verify(view, times(1)).onValidateToDateSuccess(timeSlotCan)
        Mockito.verify(view, times(1)).onValidateToTimeSuccess()

        Mockito.verify(view, times(0)).onValidateNameError(R.string.full_name_empty_err)
        Mockito.verify(view, times(0)).onValidateEmailError(R.string.email_empty_err)
        Mockito.verify(view, times(0)).onValidateEmailError(R.string.email_format_err)
        Mockito.verify(view, times(0)).onValidatePhoneNumberError(R.string.phone_number_empty_err)
        Mockito.verify(view, times(0)).onValidatePhoneNumberError(R.string.phone_number_format_err)
        Mockito.verify(view, times(0)).onValidateRoomError(R.string.room_empty_err)
        Mockito.verify(view, times(0)).onValidateReasonError(R.string.reason_empty_err)
        Mockito.verify(view, times(0)).onValidateFromDateError(R.string.from_date_empty_err)
        Mockito.verify(view, times(0)).onValidateFromTimeError(R.string.time_empty_err)
        Mockito.verify(view, times(0)).onValidateToDateError(R.string.to_date_empty_err)
        Mockito.verify(view, times(0)).onValidateToTimeError(R.string.time_empty_err)

        Mockito.verify(view, times(1)).enablePreviewButton()
    }

    @Test
    fun `when input all data incomplete incorrect should call onValidateError & enablePreviewButton`() {
        //Given
        val name = ""
        val email = "molaodds.team"
        val phoneNumber = "09876543"
        val room = ""
        val reason = "study"
        val fromDate = "2022/07/22"
        val fromTime = "13:00"
        val toDate = "2022/07/22"
        val toTime = "17:00"
        val timeSlot = arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        val timeSlotCan = arrayOf("14:00", "14:30", "15:00", "15:30", "16:00", "16:30", "17:00", "17:30", "18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00", "22:30", "23:00")


        //When
        presenter.validateFullName(name)
        presenter.validateEmail(email)
        presenter.validatePhoneNumber(phoneNumber)
        presenter.validateRoom(room)
        presenter.validateReason(reason)
        presenter.validateFromDate(fromDate)
        presenter.validateFromTime(fromTime,fromDate,toDate)
        presenter.validateToDate(fromDate, toDate, fromTime)
        presenter.validateToTime(toTime)
        //Then
        Mockito.verify(view, times(0)).onValidateNameSuccess()
        Mockito.verify(view, times(0)).onValidateEmailSuccess()
        Mockito.verify(view, times(0)).onValidatePhoneNumberSuccess()
        Mockito.verify(view, times(0)).onValidateRoomSuccess()
        Mockito.verify(view, times(1)).onValidateReasonSuccess()
        Mockito.verify(view, times(1)).onValidateFromDateSuccess(timeSlot)
        Mockito.verify(view, times(1)).onValidateFromTimeSuccess(timeSlotCan)
        Mockito.verify(view, times(1)).onValidateToDateSuccess(timeSlotCan)
        Mockito.verify(view, times(1)).onValidateToTimeSuccess()

        Mockito.verify(view, times(1)).onValidateNameError(R.string.full_name_empty_err)
        Mockito.verify(view, times(0)).onValidateEmailError(R.string.email_empty_err)
        Mockito.verify(view, times(1)).onValidateEmailError(R.string.email_format_err)
        Mockito.verify(view, times(0)).onValidatePhoneNumberError(R.string.phone_number_empty_err)
        Mockito.verify(view, times(1)).onValidatePhoneNumberError(R.string.phone_number_format_err)
        Mockito.verify(view, times(1)).onValidateRoomError(R.string.room_empty_err)
        Mockito.verify(view, times(0)).onValidateReasonError(R.string.reason_empty_err)
        Mockito.verify(view, times(0)).onValidateFromDateError(R.string.from_date_empty_err)
        Mockito.verify(view, times(0)).onValidateFromTimeError(R.string.time_empty_err)
        Mockito.verify(view, times(0)).onValidateToDateError(R.string.to_date_empty_err)
        Mockito.verify(view, times(0)).onValidateToTimeError(R.string.time_empty_err)

        Mockito.verify(view, times(0)).enablePreviewButton()
    }
}