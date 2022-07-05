package com.odds.oddsbooking.booking.form

import com.odds.oddsbooking.R
import com.odds.oddsbooking.presentations.booking.form.BookingFormPresenter
import com.odds.oddsbooking.presentations.booking.form.BookingFormView
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import org.mockito.kotlin.times

@RunWith(MockitoJUnitRunner::class)
class BookingFormPresenterTest {

    @Mock
    lateinit var view: BookingFormView
    private var presenter: BookingFormPresenter = BookingFormPresenter()

    @Before
    fun setup() {
        presenter.attachView(view)
    }

    @Test
    fun `when input fullName correct should call onValidateNameSuccess & DisablePreviewButton`() {
        //Given
        val name = "Mola Mola"
        //When
        presenter.validateFullName(name)
        //Then
        verify(view).onValidateNameSuccess()
        verify(view, never()).onValidateNameError(R.string.full_name_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input fullName incorrect should call onValidateNameError & DisablePreviewButton`() {
        //Given
        val name = ""
        //When
        presenter.validateFullName(name)
        //Then
        verify(view, never()).onValidateNameSuccess()
        verify(view).onValidateNameError(R.string.full_name_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input email correct should call onValidateEmailSuccess & DisablePreviewButton`() {
        //Given
        val email = "molamola@gmail.com"
        //When
        presenter.validateEmail(email)
        //Then
        verify(view).onValidateEmailSuccess()
        verify(view, never()).onValidateEmailError(R.string.email_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input email empty should call onValidateEmailError & DisablePreviewButton`() {
        //Given
        val email = ""
        //When
        presenter.validateEmail(email)
        //Then
        verify(view, never()).onValidateEmailSuccess()
        verify(view).onValidateEmailError(R.string.email_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input email incorrect should call onValidateEmailError & DisablePreviewButton`() {
        //Given
        val email = "email"
        //When
        presenter.validateEmail(email)
        //Then
        verify(view, never()).onValidateEmailSuccess()
        verify(view).onValidateEmailError(R.string.email_format_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input phoneNumber empty should call onValidatePhoneNumberError & DisablePreviewButton`() {
        //Given
        val phoneNumber = ""
        //When
        presenter.validatePhoneNumber(phoneNumber)
        //Then
        verify(view, never()).onValidatePhoneNumberSuccess()
        verify(view).onValidatePhoneNumberError(R.string.phone_number_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input phoneNumber incorrect should call onValidatePhoneNumberError & DisablePreviewButton`() {
        //Given
        val phoneNumber = "0123456789"
        //When
        presenter.validatePhoneNumber(phoneNumber)
        //Then
        verify(view, never()).onValidatePhoneNumberSuccess()
        verify(view).onValidatePhoneNumberError(R.string.phone_number_format_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input phoneNumber correct should call onValidatePhoneNumberSuccess & DisablePreviewButton`() {
        //Given
        val phoneNumber = "0823456789"
        //When
        presenter.validatePhoneNumber(phoneNumber)
        //Then
        verify(view).onValidatePhoneNumberSuccess()
        verify(view, never()).onValidatePhoneNumberError(R.string.phone_number_format_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when do not select room should call onValidateRoomError & DisablePreviewButton`() {
        //Given
        val room = ""
        //When
        presenter.validateRoom(room)
        //Then
        verify(view, never()).onValidateRoomSuccess()
        verify(view).onValidateRoomError(R.string.room_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when select room correct should call onValidateRoomSuccess & DisablePreviewButton`() {
        //Given
        val room = "Neon"
        //When
        presenter.validateRoom(room)
        //Then
        verify(view).onValidateRoomSuccess()
        verify(view, never()).onValidateRoomError(R.string.room_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when do not input reason empty should call onValidateReasonError & DisablePreviewButton`() {
        //Given
        val reason = ""
        //When
        presenter.validateReason(reason)
        //Then
        verify(view, never()).onValidateReasonSuccess()
        verify(view).onValidateReasonError(R.string.reason_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when input reason correct should call onValidateReasonSuccess & DisablePreviewButton`() {
        //Given
        val reason = "study"
        //When
        presenter.validateReason(reason)
        //Then
        verify(view).onValidateReasonSuccess()
        verify(view, never()).onValidateReasonError(R.string.reason_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when select fromDate correct should call onValidateFromDateSuccess & DisablePreviewButton`() {
        //Given
        val fromDate = "2022/07/22"
        val timeSlot =
            arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        //When
        presenter.validateFromDate(fromDate)
        //Then
        verify(view).onValidateFromDateSuccess(timeSlot)
        verify(view, never()).onValidateFromDateError(R.string.from_date_empty_err)
        verify(view).disablePreviewButton()
        verify(view).setEnableFromTime()
        verify(view).setFromTimeDropdown(timeSlot)
        verify(view).clearValueFromTimeDropdown()
        verify(view).clearValueToTimeDropdown()
        verify(view).setDisableToDate()
        verify(view).setDisableToTime()
    }

    @Test
    fun `when do not select fromDate incorrect should call onValidateFromDateError & DisablePreviewButton`() {
        //Given
        val fromDate = ""
        val timeSlot = arrayOf("")
        //When
        presenter.validateFromDate(fromDate)
        //Then
        verify(view, never()).onValidateFromDateSuccess(timeSlot)
        verify(view).onValidateFromDateError(R.string.from_date_empty_err)
        verify(view).disablePreviewButton()
        verify(view, never()).setEnableFromTime()
        verify(view, never()).setFromTimeDropdown(timeSlot)
        verify(view, never()).clearValueFromTimeDropdown()
        verify(view, never()).clearValueToTimeDropdown()
        verify(view, never()).setDisableToDate()
        verify(view, never()).setDisableToTime()
    }

    @Test
    fun `when select fromTime correct should call onValidateFromTimeSuccess & DisablePreviewButton`() {
        //Given
        val fromTime = "13:00"
        val timeSlot = arrayOf(
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30",
            "18:00",
            "18:30",
            "19:00",
            "19:30",
            "20:00",
            "20:30",
            "21:00",
            "21:30",
            "22:00",
            "22:30",
            "23:00"
        )
        //When
        presenter.validateFromTime(fromTime, "2022/07/22", "2022/07/22")
        //Then
        verify(view).onValidateFromTimeSuccess(timeSlot)
        verify(view, never()).onValidateFromTimeError(R.string.time_empty_err)
        verify(view).disablePreviewButton()
        verify(view).setToTimeDropDown(timeSlot)
        verify(view).clearValueToTimeDropdown()
        verify(view).setEnableToDate()
        verify(view).setEnableToTime()
    }

    @Test
    fun `when do not select fromTime incorrect should call onValidateFromTimeError & DisablePreviewButton`() {
        //Given
        val fromTime = ""
        val timeSlot = arrayOf("")
        //When
        presenter.validateFromTime(fromTime, "2022/07/22", "2022/07/22")
        //Then
        verify(view, never()).onValidateFromTimeSuccess(timeSlot)
        verify(view).onValidateFromTimeError(R.string.time_empty_err)
        verify(view).disablePreviewButton()
        verify(view, never()).setToTimeDropDown(timeSlot)
        verify(view, never()).clearValueToTimeDropdown()
        verify(view, never()).setEnableToDate()
        verify(view, never()).setEnableToTime()
    }

    @Test
    fun `when select toDate correct should call onValidateToDateSuccess & DisablePreviewButton`() {
        //Given
        val fromDate = "2022/07/22"
        val toDate = "2022/07/22"
        val timeSlot = arrayOf(
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30",
            "18:00",
            "18:30",
            "19:00",
            "19:30",
            "20:00",
            "20:30",
            "21:00",
            "21:30",
            "22:00",
            "22:30",
            "23:00"
        )
        //When
        presenter.validateToDate(fromDate, toDate, "13:00")
        //Then
        verify(view).onValidateToDateSuccess(timeSlot)
        verify(view, never()).onValidateToDateError(R.string.to_date_empty_err)
        verify(view).disablePreviewButton()
        verify(view).setEnableToTime()
        verify(view).clearValueToTimeDropdown()
    }

    @Test
    fun `when do not select toDate incorrect should call onValidateToDateError & DisablePreviewButton`() {
        //Given
        val timeSlot =
            arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        //When
        presenter.validateToDate("", "2022/07/22", "13:00")
        //Then
        verify(view, never()).onValidateToDateSuccess(timeSlot)
        verify(view).onValidateToDateError(R.string.to_date_empty_err)
        verify(view).disablePreviewButton()
        verify(view, never()).setEnableToTime()
        verify(view, never()).clearValueToTimeDropdown()
    }

    @Test
    fun `when select toTime correct should call onValidateToTimeSuccess & DisablePreviewButton`() {
        //Given
        val toTime = "17:00"
        //When
        presenter.validateToTime(toTime)
        //Then
        verify(view).onValidateToTimeSuccess()
        verify(view, never()).onValidateToTimeError(R.string.time_empty_err)
        verify(view).disablePreviewButton()
    }

    @Test
    fun `when do not select toTime incorrect should call onValidateToTimeError & DisablePreviewButton`() {
        //Given
        val toTime = ""
        //When
        presenter.validateToTime(toTime)
        //Then
        verify(view, never()).onValidateToTimeSuccess()
        verify(view).onValidateToTimeError(R.string.time_empty_err)
        verify(view).disablePreviewButton()
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
        val timeSlot =
            arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        val timeSlotCan = arrayOf(
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30",
            "18:00",
            "18:30",
            "19:00",
            "19:30",
            "20:00",
            "20:30",
            "21:00",
            "21:30",
            "22:00",
            "22:30",
            "23:00"
        )


        //When
        presenter.validateFullName(name)
        presenter.validateEmail(email)
        presenter.validatePhoneNumber(phoneNumber)
        presenter.validateRoom(room)
        presenter.validateReason(reason)
        presenter.validateFromDate(fromDate)
        presenter.validateFromTime(fromTime, fromDate, toDate)
        presenter.validateToDate(fromDate, toDate, fromTime)
        presenter.validateToTime(toTime)
        //Then
        verify(view).onValidateNameSuccess()
        verify(view).onValidateEmailSuccess()
        verify(view).onValidatePhoneNumberSuccess()
        verify(view).onValidateRoomSuccess()
        verify(view).onValidateReasonSuccess()
        verify(view).onValidateFromDateSuccess(timeSlot)
        verify(view).onValidateFromTimeSuccess(timeSlotCan)
        verify(view).onValidateToDateSuccess(timeSlotCan)
        verify(view).onValidateToTimeSuccess()

        verify(view, never()).onValidateNameError(R.string.full_name_empty_err)
        verify(view, never()).onValidateEmailError(R.string.email_empty_err)
        verify(view, never()).onValidateEmailError(R.string.email_format_err)
        verify(view, never()).onValidatePhoneNumberError(R.string.phone_number_empty_err)
        verify(view, never()).onValidatePhoneNumberError(R.string.phone_number_format_err)
        verify(view, never()).onValidateRoomError(R.string.room_empty_err)
        verify(view, never()).onValidateReasonError(R.string.reason_empty_err)
        verify(view, never()).onValidateFromDateError(R.string.from_date_empty_err)
        verify(view, never()).onValidateFromTimeError(R.string.time_empty_err)
        verify(view, never()).onValidateToDateError(R.string.to_date_empty_err)
        verify(view, never()).onValidateToTimeError(R.string.time_empty_err)

        verify(view).enablePreviewButton()
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
        val timeSlot =
            arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        val timeSlotCan = arrayOf(
            "14:00",
            "14:30",
            "15:00",
            "15:30",
            "16:00",
            "16:30",
            "17:00",
            "17:30",
            "18:00",
            "18:30",
            "19:00",
            "19:30",
            "20:00",
            "20:30",
            "21:00",
            "21:30",
            "22:00",
            "22:30",
            "23:00"
        )


        //When
        presenter.validateFullName(name)
        presenter.validateEmail(email)
        presenter.validatePhoneNumber(phoneNumber)
        presenter.validateRoom(room)
        presenter.validateReason(reason)
        presenter.validateFromDate(fromDate)
        presenter.validateFromTime(fromTime, fromDate, toDate)
        presenter.validateToDate(fromDate, toDate, fromTime)
        presenter.validateToTime(toTime)
        //Then
        verify(view, never()).onValidateNameSuccess()
        verify(view, never()).onValidateEmailSuccess()
        verify(view, never()).onValidatePhoneNumberSuccess()
        verify(view, never()).onValidateRoomSuccess()
        verify(view).onValidateReasonSuccess()
        verify(view).onValidateFromDateSuccess(timeSlot)
        verify(view).onValidateFromTimeSuccess(timeSlotCan)
        verify(view).onValidateToDateSuccess(timeSlotCan)
        verify(view).onValidateToTimeSuccess()

        verify(view).onValidateNameError(R.string.full_name_empty_err)
        verify(view, never()).onValidateEmailError(R.string.email_empty_err)
        verify(view).onValidateEmailError(R.string.email_format_err)
        verify(view, never()).onValidatePhoneNumberError(R.string.phone_number_empty_err)
        verify(view).onValidatePhoneNumberError(R.string.phone_number_format_err)
        verify(view).onValidateRoomError(R.string.room_empty_err)
        verify(view, never()).onValidateReasonError(R.string.reason_empty_err)
        verify(view, never()).onValidateFromDateError(R.string.from_date_empty_err)
        verify(view, never()).onValidateFromTimeError(R.string.time_empty_err)
        verify(view, never()).onValidateToDateError(R.string.to_date_empty_err)
        verify(view, never()).onValidateToTimeError(R.string.time_empty_err)

        verify(view, never()).enablePreviewButton()
    }

//    @Test
//    fun `When start time min with zero 00`() {
//        //Given
//        val startTime = "9:00"
//        val endTime = "11:00"
//        //When
//        presenter.getTimeSlot(startTime, endTime)
//        //Then
//
//    }
//
//    @Test
//    fun `When start time min with half 30`() {
//        //Given
//        val startTime = "9:30"
//        val endTime = "11:30"
//        //When
//        presenter.getTimeSlot(startTime, endTime)
//        //Then
//    }

    @Test
    fun `when date is saturday`() {
        //Given
        val fromDate = "2022/07/23"
        val timeSlot = arrayOf("9:00", "9:30",
            "10:00", "10:30",
            "11:00", "11:30",
            "12:00", "12:30",
            "13:00", "13:30",
            "14:00", "14:30",
            "15:00", "15:30",
            "16:00", "16:30",
            "17:00", "17:30",
            "18:00", "18:30",
            "19:00", "19:30",
            "20:00"
            )
        //When
        presenter.validateFromDate(fromDate)
        //Then
        verify(view).onValidateFromDateSuccess(timeSlot)
    }

    @Test
    fun `when date is sunday`() {
        //Given
        val fromDate = "2022/07/24"
        val timeSlot = arrayOf("9:00", "9:30",
            "10:00", "10:30",
            "11:00", "11:30",
            "12:00", "12:30",
            "13:00", "13:30",
            "14:00", "14:30",
            "15:00", "15:30",
            "16:00", "16:30",
            "17:00", "17:30",
            "18:00", "18:30",
            "19:00", "19:30",
            "20:00"
            )
        //When
        presenter.validateFromDate(fromDate)
        //Then
        verify(view).onValidateFromDateSuccess(timeSlot)
    }
}