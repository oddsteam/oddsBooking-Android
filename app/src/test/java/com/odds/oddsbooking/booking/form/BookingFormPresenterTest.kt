package com.odds.oddsbooking.booking.form

import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.presentations.booking.form.BookingFormPresenter
import com.odds.oddsbooking.presentations.booking.form.BookingFormView
import org.junit.Assert
import org.junit.Test

class BookingFormPresenterTest {
    @Test
    fun `when input fullName correct should call onValidateNameSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validateFullName("test")
        // Assert
        val exceptFormError = 0
        val expectFormSuccess = 1
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input fullName incorrect should call onValidateNameError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validateFullName("")
        // Assert
        val exceptFormError = 1
        val expectFormSuccess = 0
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

//    @Test
//    fun `when input email correct should call onValidateEmailSuccess & DisablePreviewButton`() {
//        // Arrange
//        val presenter = BookingFormPresenter()
//        val view = SpyBookingFormView()
//        presenter.attachView(view)
//        // Act
//        presenter.validateEmail("mola@odds.team")
//        // Assert
//        val exceptFormError = 0
//        val expectFormSuccess = 1
//        val expectDisablePreviewButton = 1
//        Assert.assertEquals(exceptFormError, view.spyFormError)
//        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
//        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
//    }

    @Test
    fun `when input phoneNumber correct should call onValidatePhoneNumberSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validatePhoneNumber("0655555555")
        // Assert
        val exceptFormError = 0
        val expectFormSuccess = 1
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input phoneNumber incorrect should call onValidatePhoneNumberError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validatePhoneNumber("1234567890")
        // Assert
        val exceptFormError = 1
        val expectFormSuccess = 0
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input phoneNumber empty should call onValidatePhoneNumberError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validatePhoneNumber("")
        // Assert
        val exceptFormError = 1
        val expectFormSuccess = 0
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when select room correct should call onValidateRoomSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validateRoom("Neon")
        // Assert
        val exceptFormError = 0
        val expectFormSuccess = 1
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when select room incorrect should call onValidateRoomError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validateRoom("")
        // Assert
        val exceptFormError = 1
        val expectFormSuccess = 0
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when input reason correct should call onValidateReasonSuccess & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validateReason("Study")
        // Assert
        val exceptFormError = 0
        val expectFormSuccess = 1
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    @Test
    fun `when reason is empty should call onValidateReasonError & DisablePreviewButton`() {
        // Arrange
        val presenter = BookingFormPresenter()
        val view = SpyBookingFormView()
        presenter.attachView(view)
        // Act
        presenter.validateReason("")
        // Assert
        val exceptFormError = 1
        val expectFormSuccess = 0
        val expectDisablePreviewButton = 1
        Assert.assertEquals(exceptFormError, view.spyFormError)
        Assert.assertEquals(expectFormSuccess, view.spyFormSuccess)
        Assert.assertEquals(expectDisablePreviewButton, view.spyDisablePreviewButton)
    }

    class SpyBookingFormView : BookingFormView {
        var spyDisablePreviewButton = 0
        var spyEnablePreviewButton = 0
        var spyFormError = 0
        var spyFormSuccess = 0

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
            spyFormError += 1
        }

        override fun onValidateNameSuccess() {
            spyFormSuccess += 1
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
            TODO("Not yet implemented")
        }

        override fun onValidateFromDateSuccess(timeSlot: Array<String>) {
            TODO("Not yet implemented")
        }

        override fun onValidateFromTimeError(errMsg: Int) {
            TODO("Not yet implemented")
        }

        override fun onValidateFromTimeSuccess(timeSlot: Array<String>) {
            TODO("Not yet implemented")
        }

        override fun onValidateToDateError(errMsg: Int) {
            TODO("Not yet implemented")
        }

        override fun onValidateToDateSuccess(timeSlot: Array<String>) {
            TODO("Not yet implemented")
        }

        override fun onValidateToTimeError(errMsg: Int) {
            TODO("Not yet implemented")
        }

        override fun onValidateToTimeSuccess() {
            TODO("Not yet implemented")
        }

        override fun onDatePickerDialogFormDate(fromDate: DateInTimePicker) {
            TODO("Not yet implemented")
        }

        override fun onDatePickerDialogToDate(toDate: DateInTimePicker) {
            TODO("Not yet implemented")
        }

        override fun setFromTimeDropdown(timeSlot: Array<String>) {
            TODO("Not yet implemented")
        }

        override fun setToTimeDropDown(timeSlot: Array<String>) {
            TODO("Not yet implemented")
        }

        override fun clearValueFromTimeDropdown() {
            TODO("Not yet implemented")
        }

        override fun clearValueToTimeDropdown() {
            TODO("Not yet implemented")
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
            TODO("Not yet implemented")
        }

        override fun setDisableFromTime() {
            TODO("Not yet implemented")
        }

        override fun setEnableToDate() {
            TODO("Not yet implemented")
        }

        override fun setDisableToDate() {
            TODO("Not yet implemented")
        }

        override fun setEnableToTime() {
            TODO("Not yet implemented")
        }

        override fun setDisableToTime() {
            TODO("Not yet implemented")
        }

        override fun setTextFromDate(date: String) {
            TODO("Not yet implemented")
        }

        override fun setTextToDate(date: String) {
            TODO("Not yet implemented")
        }

    }
}