package com.odds.oddsbooking.booking.form

import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.presentations.booking.form.BookingFormPresenter
import com.odds.oddsbooking.presentations.booking.form.BookingFormView
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class BookingFormPresenterTest {

    @Before
    fun setup(){
        val view = SpyBookingFormView()

        val presenter = BookingFormPresenter()

        presenter.attachView(view)
    }


    @Test
    fun `when input fullName correct should call onValidateNameSuccess & DisablePreviewButton`() {
        // Arrange

        // Act
//        presenter.validateFullName("test")
        // Assert
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input fullName incorrect should call onValidateNameError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validateFullName("")
        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input phoneNumber correct should call onValidatePhoneNumberSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validatePhoneNumber("0655555555")
//        // Assert
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input phoneNumber incorrect should call onValidatePhoneNumberError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validatePhoneNumber("1234567890")
//        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input phoneNumber empty should call onValidatePhoneNumberError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validatePhoneNumber("")
//        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when select room correct should call onValidateRoomSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validateRoom("Neon")
//        // Assert
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when select room incorrect should call onValidateRoomError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validateRoom("")
//        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input reason correct should call onValidateReasonSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validateReason("Study")
//        // Assert
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when reason is empty should call onValidateReasonError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validateReason("")
//        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when formDate is select should call onValidateSuccess & DisablePreviewButton`() {

        // Arrange Given -> ให้ว่า....
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        val fromDate = "2022/07/12"
//        // Act When -> เมื่อ... (จาก Given)
//        presenter.validateFromDate(fromDate)
//        // Assert Then -> จะเกิดอะไรขึ้น
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        val expectEnableFormTimePicker = 1
//        val expectTimePickerDropDown = 1
//        val expectClearFormTimeDropDown = 1
//        val expectClearToTimeDropDown = 1
//        val expectSetDisableToDate = 1
//        val expectSetDisableToTime = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
//        Assert.assertEquals(expectEnableFormTimePicker, view.spyFormTimePickerEnable)
//        Assert.assertEquals(expectTimePickerDropDown, view.spyFormTimePickerDropdown)
//        Assert.assertEquals(expectClearFormTimeDropDown, view.spyClearFormTimeDropDown)
//        Assert.assertEquals(expectClearToTimeDropDown, view.spyClearToTimeDropDown)
//        Assert.assertEquals(expectSetDisableToDate, view.spySetDisableToDate)
//        Assert.assertEquals(expectSetDisableToTime, view.spySetDisableToTime)
    }

    @Test
    fun `when formDate is not select should call onValidateError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
//        presenter.validateFromDate("")
//        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        val expectEnableFormTimePicker = 0
//        val expectTimePickerDropDown = 0
//        val expectClearFormTimeDropDown = 0
//        val expectClearToTimeDropDown = 0
//        val expectSetDisableToDate = 0
//        val expectSetDisableToTime = 0
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
//        Assert.assertEquals(expectEnableFormTimePicker, view.spyFormTimePickerEnable)
//        Assert.assertEquals(expectTimePickerDropDown, view.spyFormTimePickerDropdown)
//        Assert.assertEquals(expectClearFormTimeDropDown, view.spyClearFormTimeDropDown)
//        Assert.assertEquals(expectClearToTimeDropDown, view.spyClearToTimeDropDown)
//        Assert.assertEquals(expectSetDisableToDate, view.spySetDisableToDate)
//        Assert.assertEquals(expectSetDisableToTime, view.spySetDisableToTime)
    }


    @Test
    fun `when formTime is not select should call onValidateSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
//        // Act
//        presenter.validateFromTime("", "2022/07/12", "2022/07/12")
//        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        val expectSetToTimeDropDown = 0
//        val expectClearToTimeDropDown = 0
//        val expectSetEnableToDate = 0
//        val expectSetEnableToTime = 0
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
//        Assert.assertEquals(expectSetToTimeDropDown, view.spySetToTimeDropDown)
//        Assert.assertEquals(expectClearToTimeDropDown, view.spyClearToTimeDropDown)
//        Assert.assertEquals(expectSetEnableToDate, view.spySetEnableToDate)
//        Assert.assertEquals(expectSetEnableToTime, view.spySetEnableToTime)
    }


    @Test
    fun `when toDate is select should call onValidateToDateSuccess & DisablePreviewButton`() {
        //Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
//        //Act
//        presenter.validateToDate("2022/07/12", "2022/07/12", "18:00")
//        //Assert
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        val expectSetToDateDropDown = 1
//        val expectEnableToTime = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
//        Assert.assertEquals(expectSetToDateDropDown, view.spySetToTimeDropDown)
//        Assert.assertEquals(expectEnableToTime, view.spySetEnableToTime)
    }

    @Test
    fun `when toDate is select should call onValidateToDateError & DisablePreviewButton`() {
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
//        //Act
//        presenter.validateToDate("", "", "")
//        //Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        val expectSetToDateDropDown = 0
//        val expectEnableToTime = 0
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
//        Assert.assertEquals(expectSetToDateDropDown, view.spySetToTimeDropDown)
//        Assert.assertEquals(expectEnableToTime, view.spySetEnableToTime)
    }

    @Test
    fun `when toTime is select should call onValidateSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
//        // Act
//        presenter.validateToTime("13:00")
//        // Assert
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when toTime is not select should call onValidateSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
//        // Act
//        presenter.validateToTime("")
//        // Assert
//        val exceptFormError = 1
//        val expectFormSuccess = 0
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    //TODO: validateForm
    //TODO: onFromDateClick
    //TODO: onToDateClick
    //TODO: autoFormatName
    //TODO: setFromTimesDropDown
    //TODO: setToTimesDropDown
    //TODO: onDatePickerCancel
    //TODO: onDatePickerDismiss
    //TODO: onDatePickerConfirm
    //TODO: onPreviewButtonClicked


    class SpyBookingFormView : BookingFormView {
        var spyDisablePreviewButton = 0
        var spyEnablePreviewButton = 0
        var spyFormError = 0
        var spyFormSuccess = 0
        var spyFormTimePickerEnable = 0
        var spyFormTimePickerDropdown = 0
        var spyClearFormTimeDropDown = 0
        var spyClearToTimeDropDown = 0
        var spySetDisableToDate = 0
        var spySetDisableToTime = 0
        var spySetToTimeDropDown = 0
        var spySetEnableToDate = 0
        var spySetEnableToTime = 0

        override fun onNameAutoFormat(name: String) {
            TODO("Not yet implemented")
        }

        override fun enablePreviewButton() {
            spyEnablePreviewButton++
        }

        override fun disablePreviewButton() {
            spyDisablePreviewButton++
        }

        override fun onValidateNameError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateNameSuccess() {
            spyFormSuccess++
        }

        override fun onValidateEmailError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateEmailSuccess() {
            spyFormSuccess++
        }

        override fun onValidatePhoneNumberError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidatePhoneNumberSuccess() {
            spyFormSuccess++
        }

        override fun onValidateRoomError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateRoomSuccess() {
            spyFormSuccess++
        }

        override fun onValidateReasonError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateReasonSuccess() {
            spyFormSuccess++
        }

        override fun onValidateFromDateError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateFromDateSuccess(timeSlot: Array<String>) {
            spyFormSuccess++
        }

        override fun onValidateFromTimeError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateFromTimeSuccess(timeSlot: Array<String>) {
            spyFormSuccess++
        }

        override fun onValidateToDateError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateToDateSuccess(timeSlot: Array<String>) {
            spyFormSuccess++
        }

        override fun onValidateToTimeError(errMsg: Int) {
            spyFormError++
        }

        override fun onValidateToTimeSuccess() {
            spyFormSuccess++
        }

        override fun onDatePickerDialogFormDate(fromDate: DateInTimePicker) {
            TODO("Not yet implemented")
        }

        override fun onDatePickerDialogToDate(toDate: DateInTimePicker) {
            TODO("Not yet implemented")
        }

        override fun setFromTimeDropdown(timeSlot: Array<String>) {
            spyFormTimePickerDropdown++
        }

        override fun setToTimeDropDown(timeSlot: Array<String>) {
            spySetToTimeDropDown++
        }

        override fun clearValueFromTimeDropdown() {
            spyClearFormTimeDropDown++
        }

        override fun clearValueToTimeDropdown() {
            spyClearToTimeDropDown++
        }

        override fun onNavigateToPreview(bookingData: BookingData) {
            TODO("Not yet implemented")
        }

        override fun setDisableFromDateEditText() {
            TODO("Not yet implemented")
        }

        override fun setDisableToDateEditText() {
            TODO("Not yet implemented")
        }

        override fun setEnableFromTime() {
            spyFormTimePickerEnable++
        }

        override fun setDisableFromTime() {
            TODO("Not yet implemented")
        }

        override fun setEnableToDate() {
            spySetEnableToDate++
        }

        override fun setDisableToDate() {
            spySetDisableToDate++
        }

        override fun setEnableToTime() {
            spySetEnableToTime++
        }

        override fun setDisableToTime() {
            spySetDisableToTime++
        }

        override fun setTextFromDate(date: String) {
            TODO("Not yet implemented")
        }

        override fun setTextToDate(date: String) {
            TODO("Not yet implemented")
        }

    }
}