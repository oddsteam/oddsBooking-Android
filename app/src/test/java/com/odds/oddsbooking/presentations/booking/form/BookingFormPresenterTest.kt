package com.odds.oddsbooking.presentations.booking.form

import com.odds.oddsbooking.R
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.models.DateInTimePickerType
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.never
import java.text.SimpleDateFormat
import java.util.*

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
    fun `when select toDate correct but fromTime's empty should call DisablePreviewButton only`() {
        //Given
        val fromDate = "2022/07/22"
        val fromTime = ""
        val toDate = "2022/07/22"
        val timeSlot =
            arrayOf("18:00", "18:30", "19:00", "19:30", "20:00", "20:30", "21:00", "21:30", "22:00")
        //When
        presenter.validateToDate(fromDate, toDate, fromTime)
        //Then
        verify(view, never()).onValidateToDateSuccess(timeSlot)
        verify(view, never()).onValidateToDateError(R.string.to_date_empty_err)
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

    @Test
    fun `when input date is saturday should call onValidateFromDateSuccess`() {
        //Given
        val fromDate = "2022/07/23"
        val timeSlot = arrayOf(
            "9:00", "9:30",
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
    fun `when input date is sunday should call onValidateFromDateSuccess`() {
        //Given
        val fromDate = "2022/07/24"
        val timeSlot = arrayOf(
            "9:00", "9:30",
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
    fun `when booking is the same day on weekend should call onValidateFromTimeSuccess`() {
        //Given
        val fromTime = "10:00"
        val fromDate = "2022/07/23"
        val toDate = "2022/07/23"
        val timeSlot = arrayOf(
            "11:00", "11:30",
            "12:00", "12:30",
            "13:00", "13:30",
            "14:00", "14:30",
            "15:00", "15:30",
            "16:00", "16:30",
            "17:00", "17:30",
            "18:00", "18:30",
            "19:00", "19:30",
            "20:00", "20:30",
            "21:00"
        )
        //When
        presenter.validateFromTime(fromTime, fromDate, toDate)
        //Then
        verify(view).onValidateFromTimeSuccess(timeSlot)
    }

    @Test
    fun `when booking fromDate is saturday & toDate is sunday should call onValidateFromTimeSuccess`() {
        //Given
        val fromTime = "10:00"
        val fromDate = "2022/07/23"
        val toDate = "2022/07/24"
        val timeSlot = arrayOf(
            "9:00", "9:30",
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
            "20:00", "20:30",
            "21:00"
        )
        //When
        presenter.validateFromTime(fromTime, fromDate, toDate)
        //Then
        verify(view).onValidateFromTimeSuccess(timeSlot)
    }

    @Test
    fun `when select Saturday in toDate which is the same day from fromDate should call onValidateToDateSuccess`() {
        //Given
        val fromDate = "2022/07/23"
        val toDate = "2022/07/23"
        val fromTime = "10:00"
        val timeSlot = arrayOf(
            "11:00", "11:30",
            "12:00", "12:30",
            "13:00", "13:30",
            "14:00", "14:30",
            "15:00", "15:30",
            "16:00", "16:30",
            "17:00", "17:30",
            "18:00", "18:30",
            "19:00", "19:30",
            "20:00", "20:30",
            "21:00"
        )
        //When
        presenter.validateToDate(fromDate, toDate, fromTime)
        //Then
        verify(view).onValidateToDateSuccess(timeSlot)
    }

    @Test
    fun `when select Sunday in toDate which is the other day from fromDate should call onValidateToDateSuccess`() {
        //Given
        val fromDate = "2022/07/23"
        val toDate = "2022/07/24"
        val fromTime = "10:00"
        val timeSlot = arrayOf(
            "9:00", "9:30",
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
            "20:00", "20:30",
            "21:00"
        )
        //When
        presenter.validateToDate(fromDate, toDate, fromTime)
        //Then
        verify(view).onValidateToDateSuccess(timeSlot)
    }

    @Test
    fun `when startTime and endTime has same hrs and same date`() {
        //Given
        val startTime = "10:00"
        val endTime = "10:30"
        val timeSlot = arrayOf(
            "10:00"
        )
        //When
        val newTimeSlot = presenter.getTimeSlot(startTime, endTime)
        //Then
        Assert.assertArrayEquals(timeSlot, newTimeSlot)
    }

    @Test
    fun `when click fromDate should call fun onFromDateClick & onDatePickerDialogFormDate`() {
        //Given
        val fromDate = "2022/07/19"
        val dateInTimePickerDialog = DateInTimePicker(
            DateInTimePickerType.FROM_DATE,
            System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
            null,
            fromDate
        )

        //When
        presenter.onFromDateClick(fromDate)
        //Then
        verify(view).onDatePickerDialogFormDate(dateInTimePickerDialog)
    }

    @Test
    fun `when input name should call onNameAutoFormat`() {
        //Given
        val name = "molA mOla"
        val nameFormatted = "Mola Mola"
        //When
        presenter.autoFormatName(name)
        //Then
        verify(view).onNameAutoFormat(nameFormatted)
    }

    @Test
    fun `when Cancel on TimePickerDialog after show DatePickerDialogFormDate should call setDisableFromDateEditText`() {
        //Given
        val fromDate = "2022/07/19"
        //When
        presenter.onFromDateClick(fromDate)
        presenter.onDatePickerCancel()
        //Then
        verify(view).setDisableFromDateEditText()
    }

    @Test
    fun `when Dismiss on TimePickerDialog after show DatePickerDialogFormDate should call setDisableFromDateEditText`() {
        //Given
        val fromDate = "2022/07/19"
        //When
        presenter.onFromDateClick(fromDate)
        presenter.onDatePickerDismiss()
        //Then
        verify(view).setDisableFromDateEditText()
    }

    @Test
    fun `when Confirm on TimePickerDialog after show DatePickerDialogFormDate should call setDisableFromDateEditText and setTextFromDate`() {
        //Given
        val fromDate = "2022/07/19"
        //When
        presenter.onFromDateClick(fromDate)
        presenter.onDatePickerConfirm(2022, 6, 19)
        //Then
        verify(view).setDisableFromDateEditText()
        verify(view).setTextFromDate("2022/07/19")
    }

    @Test
    fun `when Cancel on TimePickerDialog after show DatePickerDialogToDate should call setDisableToDateEditText`() {
        //Given
        val fromDate = "2022/07/19"
        val toDate = "2022/07/19"
        //When
        presenter.onFromDateClick(fromDate)
        presenter.onDatePickerConfirm(2022, 6, 19)
        presenter.validateFromDate(fromDate)
        presenter.onToDateClick(toDate, fromDate)
        presenter.onDatePickerCancel()
        //Then
        verify(view).setDisableToDateEditText()
    }

    @Test
    fun `when Dismiss on TimePickerDialog after show DatePickerDialogToDate should call setDisableToDateEditText`() {
        //Given
        val fromDate = "2022/07/19"
        val toDate = "2022/07/19"
        //When
        presenter.onFromDateClick(fromDate)
        presenter.onDatePickerConfirm(2022, 6, 19)
        presenter.validateFromDate(fromDate)
        presenter.onToDateClick(toDate, fromDate)
        presenter.onDatePickerDismiss()
        //Then
        verify(view).setDisableToDateEditText()
    }

    @Test
    fun `when Confirm on TimePickerDialog after show DatePickerDialogTomDate should call setDisableToDateEditText and setTextToDate`() {
        //Given
        val fromDate = "2022/07/19"
        val toDate = "2022/07/19"
        //When
        presenter.onFromDateClick(fromDate)
        presenter.onDatePickerConfirm(2022, 6, 19)
        presenter.validateFromDate(fromDate)
        presenter.onToDateClick(toDate, fromDate)
        presenter.onDatePickerConfirm(2022, 6, 19)
        //Then
        verify(view).setDisableToDateEditText()
        verify(view).setTextToDate("2022/07/19")
    }

    @Test
    fun `when setFromTimesDropDown`() {
        //Given
        val timeSlot = arrayOf(
            "9:30",
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
            "20:00", "20:30",
            "21:00"
        )
        presenter.setFromTimeTimeSlot("09:30", "21:00")

        //When
        presenter.setFromTimesDropDown()

        //Then
        verify(view).setFromTimeDropdown(timeSlot)
    }

    @Test
    fun `when setToTimesDropDown`() {
        //Given
        val timeSlot = arrayOf(
            "9:30",
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
            "20:00", "20:30",
            "21:00"
        )
        presenter.setToTimeTimeSlot("09:30", "21:00")

        //When
        presenter.setToTimesDropDown()

        //Then
        verify(view).setToTimeDropDown(timeSlot)
    }

    @Test
    fun `when click previewButton navigate to preview`() {
        //Given
        val bookingData = BookingData()
        //When
        presenter.onPreviewButtonClicked()
        //Then
        verify(view).onNavigateToPreview(bookingData)
    }

    @Test
    fun `when click toDate if fromDate's Saturday should call fun onFromDateClick & onDatePickerDialogFormDate`() {
        //Given
        val toDate = "2022/07/23" //Saturday
        val fromDate = "2022/07/23"

       var formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
       val date = formatter.parse(fromDate)

        val minTime: Long = date.time
        val maxTime: Long = date.time

        print("minDateT : " + minTime + "\n")
        print("maxDateT : " + maxTime + "\n")

        val dateInTimePickerDialog =
            DateInTimePicker(
                datePickerType = DateInTimePickerType.TO_DATE,
                minTime,
                maxTime,
                toDate
            )


        //When
        presenter.onToDateClick(toDate, fromDate)

        //Then
        verify(view).onDatePickerDialogToDate(dateInTimePickerDialog)
    }

    @Test
    fun `when click toDate if fromDate's Sunday should call fun onFromDateClick & onDatePickerDialogFormDate`() {
        //Given
        val toDate = "2022/07/24"
        val fromDate = "2022/07/24"

        var formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val date = formatter.parse(fromDate)

        val minTime: Long = date.time
        val maxTime: Long = date.time
        val dateInTimePickerDialog =
            DateInTimePicker(
                datePickerType = DateInTimePickerType.TO_DATE,
                minTime,
                maxTime,
                toDate
            )

        //When
        presenter.onToDateClick(toDate, fromDate)

        //Then
        verify(view).onDatePickerDialogToDate(dateInTimePickerDialog)
    }

    @Test
    fun `when click toDate if fromDate's weekday should call fun onFromDateClick & onDatePickerDialogFormDate`() {
        //Given
        val toDate = "2022/07/20"
        val fromDate = "2022/07/20"

        var formatter = SimpleDateFormat("yyyy/MM/dd", Locale.US)
        val date = formatter.parse(fromDate)

        val minTime: Long = date.time
        val maxTime: Long = date.time
        val dateInTimePickerDialog =
            DateInTimePicker(
                datePickerType = DateInTimePickerType.TO_DATE,
                minTime,
                maxTime,
                toDate
            )

        //When
        presenter.onToDateClick(toDate, fromDate)

        //Then
        verify(view).onDatePickerDialogToDate(dateInTimePickerDialog)
    }
}
