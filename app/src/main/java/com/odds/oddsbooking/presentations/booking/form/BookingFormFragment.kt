package com.odds.oddsbooking.presentations.booking.form

import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.CalendarDate
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.presentations.booking.BookingFormActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog


class BookingFormFragment : Fragment(), BookingFormView {

    private val binding by lazy { FragmentBookingFormBinding.inflate(layoutInflater) }

    private val presenter by lazy { BookingFormPresenter() }

    private var bookingData: BookingData = BookingData()
    private val disable = R.color.disable_color
    private val enable = android.R.color.transparent

    //region Fragment Life Cycle
    override fun onResume() {
        super.onResume()
        presenter.setFromTimesDropDown()
        presenter.setToTimesDropDown()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setDisableFromTime()
        setDisableToTime()

        loadRoomDropdown()
        formValueValidate()
        formValidate()
        dateTimePickerEnable()
        onFromDateClicked()
        onToDateClicked()
        onPreviewButtonClick()
        onReturnBinding()
    }
    //endregion

    private fun loadRoomDropdown() {
        val rooms = resources.getStringArray(R.array.rooms)
        val arrayAdapter =
            ArrayAdapter(binding.roomFormDropdown.context, R.layout.dropdown_item, rooms)
        binding.roomFormDropdown.setAdapter(arrayAdapter)
    }

    private fun showDatePickerDialog(
        dateInTimePicker: DateInTimePicker
    ) {
        val listener = onDateSetListener()
        val listenerDismiss = onDismissListener()
        val listenerCancel = onCancelListener()
        val dpd = DatePickerDialog.newInstance(
            listener,
            dateInTimePicker.currentCalendar.years,
            dateInTimePicker.currentCalendar.months,
            dateInTimePicker.currentCalendar.days
        )
        dpd.setOnDismissListener(listenerDismiss)
        dpd.setOnCancelListener(listenerCancel)
        dpd.minDate = dateInTimePicker.minDateCalendar
        if (dateInTimePicker.maxDate != null) {
            dpd.maxDate = dateInTimePicker.maxDateCalendar
        }
        dpd.setTitle("Select date")
        dpd.accentColor = ContextCompat.getColor(requireContext(), R.color.purple_color)
        dpd.show(childFragmentManager, "DatePickerDialog")
    }

    //region on...Listener
    private fun onCancelListener() =
        DialogInterface.OnCancelListener {
            presenter.onDatePickerCancel()
        }

    private fun onDismissListener() =
        DialogInterface.OnDismissListener {
            presenter.onDatePickerDismiss()
        }

    private fun onDateSetListener() = //confirm
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            presenter.onDatePickerConfirm(year, month, day)
        }
    //endregion

    //region formValidates
    private fun formValidate() {
        //TODO: in .doOnTextChange move to doAfterTextChange
        with(binding) {
            nameFormEditText.doAfterTextChanged { text ->
                presenter.validateFullName(text.toString())
                nameFormEditText.setOnFocusChangeListener { _, _ -> presenter.autoFormatName(text.toString()) }
                //TODO: move bookingData into Presenter
                bookingData.fullName = text.toString()
            }
            emailFormEditText.doAfterTextChanged { text ->
                bookingData.email = text.toString()
                presenter.validateForm()
            }
            phoneFormEditText.doAfterTextChanged { text ->
                bookingData.phoneNumber = text.toString()
                presenter.validateForm()
            }
            roomFormDropdown.doAfterTextChanged { text ->
                bookingData.room = text.toString()
                presenter.validateForm()
            }
            reasonFormEditText.doAfterTextChanged { text ->
                bookingData.reason = text.toString()
                presenter.validateForm()
            }
            fromDateFormEditText.doAfterTextChanged { text ->
                bookingData.fromDate = text.toString()
                presenter.validateForm()
            }
            fromTimeFormDropdown.doAfterTextChanged { text ->
                bookingData.fromTime = text.toString()
                presenter.validateForm()
            }
            toDateFormEditText.doAfterTextChanged { text ->
                bookingData.toDate = text.toString()
                presenter.validateForm()
            }
            toTimeFormDropDown.doAfterTextChanged { text ->
                bookingData.toTime = text.toString()
                presenter.validateForm()
            }
        }
    }

    private fun formValueValidate() {
        with(binding) {
            nameFormEditText.doOnTextChanged { text, _, _, _ ->
//                presenter.validateFullName(text.toString())
//                nameFormEditText.setOnFocusChangeListener { _, _ -> presenter.autoFormatName(text.toString()) }
            }

            emailFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateEmail(text.toString())
            }

            phoneFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validatePhoneNumber(text.toString())
            }

            roomFormDropdown.doOnTextChanged { text, _, _, _ ->
                presenter.validateRoom(text.toString())
            }

            reasonFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateReason(text.toString())
            }

            fromDateFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateFromDate(text.toString())
            }

            fromTimeFormDropdown.doOnTextChanged { text, _, _, _ ->
                presenter.validateFromTime(
                    text.toString(),
                    fromDateFormEditText.text.toString(),
                    toDateFormEditText.text.toString(),
                )
            }

            toDateFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateToDate(
                    text.toString(),
                    fromDateFormEditText.text.toString(),
                    fromTimeFormDropdown.text.toString()
                )
            }

            toTimeFormDropDown.doOnTextChanged { text, _, _, _ ->
                presenter.validateToTime(text.toString())
            }
        }
    }

    //region validate Name
    override fun onValidateNameError(errMsg: Int) {
        val container = binding.nameFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidateNameSuccess() {
        val container = binding.nameFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate Email
    override fun onValidateEmailError(errMsg: String) {
        val container = binding.emailFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateEmailSuccess() {
        val container = binding.emailFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate PhoneNumber
    override fun onValidatePhoneNumberError(errMsg: String) {
        val container = binding.phoneFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidatePhoneNumberSuccess() {
        val container = binding.phoneFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate Room
    override fun onValidateRoomError(errMsg: String) {
        val container = binding.roomFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateRoomSuccess() {
        val container = binding.roomFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate Reason
    override fun onValidateReasonError(errMsg: String) {
        val container = binding.reasonFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateReasonSuccess() {
        val container = binding.reasonFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate FromDate
    override fun onValidateFromDateError(errMsg: String) {
        val container = binding.fromDateFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateFromDateSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(fromDateFormContainer)

            fromDateFormEditText.text?.let { toDateFormEditText.text = it }
        }
    }
    //endregion

    //region validate FromTime
    override fun onValidateFromTimeError(errMsg: String) {
        val container = binding.fromTimeFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateFromTimeSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(fromTimeFormContainer)
        }
    }
    //endregion

    //region validate ToDate
    override fun onValidateToDateError(errMsg: String) {
        val container = binding.toDateFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateToDateSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(toDateFormContainer)
        }
    }
    //endregion

    //region validate ToTime
    override fun onValidateToTimeError(errMsg: String) {
        val container = binding.toTimeFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateToTimeSuccess() {
        removeErrorContainer(binding.toTimeFormContainer)
    }
    //endregion

    //endregion

    //region setEnable/Disable
    private fun setFromTimeEnable(isEnable: Boolean, backgroundColor: Int) {
        binding.fromTimeFormDropdown.isEnabled = isEnable
        binding.fromTimeFormContainer.setBoxBackgroundColorResource(backgroundColor)
        binding.fromTimeFormContainer.isEnabled = isEnable
    }

    private fun setToDateEnable(isEnable: Boolean, backgroundColor: Int) {
        binding.toDateFormContainer.isEnabled = isEnable
        binding.toDateFormContainer.setBoxBackgroundColorResource(backgroundColor)
    }

    private fun setToTimeEnable(isEnable: Boolean, backgroundColor: Int) {
        binding.toTimeFormDropDown.isEnabled = isEnable
        binding.toTimeFormContainer.setBoxBackgroundColorResource(backgroundColor)
        binding.toTimeFormContainer.isEnabled = isEnable
    }

    override fun setDisableFromDateEditText() {
        setEditTextIsFocus(binding.fromDateFormEditText, false)
    }

    override fun setDisableToDateEditText() {
        setEditTextIsFocus(binding.toDateFormEditText, false)
    }

    override fun setEnableFromTime() {
        setFromTimeEnable(true, enable)
    }

    override fun setDisableFromTime() {
        setFromTimeEnable(false, disable)
    }

    override fun setEnableToDate() {
        setToDateEnable(true, enable)
    }

    override fun setDisableToDate() {
        setToDateEnable(false, disable)
    }

    override fun setEnableToTime() {
        setToTimeEnable(true, enable)
    }

    override fun setDisableToTime() {
        setToTimeEnable(false, disable)
    }

    override fun setTextFromDate(date: String) {
        binding.fromDateFormEditText.setText(date)
    }

    override fun setTextToDate(date: String) {
        binding.toDateFormEditText.setText(date)

    }

    override fun clearValueFromTimeDropdown() {
        setDropDownWithValueToEmpty(binding.fromTimeFormDropdown)

    }

    override fun clearValueToTimeDropdown() {
        setDropDownWithValueToEmpty(binding.toTimeFormDropDown)
    }
    //endregion

    private fun setDropDownWithValueToEmpty(editText: AutoCompleteTextView) {
        if (!editText.text.isNullOrEmpty()) {
            editText.setText("")
        }
    }


    override fun onDatePickerDialogFormDate(fromDate: DateInTimePicker) {
        setEditTextIsFocus(binding.fromDateFormEditText, true)
        showDatePickerDialog(
            fromDate
        )
    }

    override fun onDatePickerDialogToDate(toDate: DateInTimePicker) {
        setEditTextIsFocus(binding.toDateFormEditText, true)
        showDatePickerDialog(
            toDate
        )
    }


    override fun setFromTimeDropdown(timeSlot: Array<String>) {
        setTimeDropdown(timeSlot, binding.fromTimeFormDropdown)
    }

    override fun setToTimeDropDown(timeSlot: Array<String>) {
        setTimeDropdown(timeSlot, binding.toTimeFormDropDown)
    }

    private fun removeErrorContainer(inputLayout: TextInputLayout) {
        inputLayout.isErrorEnabled = false
        inputLayout.error = null
    }


    override fun onNameAutoFormat(name: String) {
        binding.nameFormEditText.setText(presenter.getNameFormatter(name))
    }

    override fun enablePreviewButton() {
        val colors = resources.getColor(R.color.purple_color, null)
        binding.previewButton.isEnabled = true
        binding.previewButton.setBackgroundColor(colors)
    }

    override fun disablePreviewButton() {
        val colors = resources.getColor(R.color.gray_outline, null)
        binding.previewButton.isEnabled = false
        binding.previewButton.setBackgroundColor(colors)
    }

    private fun setTimeDropdown(timeSlot: Array<String>, view: View) {
        val toTime: Array<String> = timeSlot
        val arrayAdapterToTime = ArrayAdapter(view.context, R.layout.dropdown_item, toTime)
        val dropdown = binding.root.findViewById<AutoCompleteTextView>(view.id)
        dropdown.setAdapter(arrayAdapterToTime)
    }

    private fun dateTimePickerEnable() {
        with(binding) {
            fromTimeFormDropdown.isEnabled = fromDateFormEditText.text?.isNotEmpty() == true
            toTimeFormDropDown.isEnabled = fromDateFormEditText.text?.isNotEmpty() == true
            toDateFormEditText.isEnabled = fromDateFormEditText.text?.isNotEmpty() == true
        }
    }

    private fun onPreviewButtonClick() {
        binding.previewButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.bookingPreviewFragment,
                    bundleOf(
                        BookingFormActivity.EXTRA_BOOKING to bookingData
                    )
                )
            }
        }
    }

    private fun onFromDateClicked() {
        binding.fromDateFormEditText.setOnClickListener {
            presenter.onFromDateClick(binding.fromDateFormEditText.text.toString())
        }
    }

    private fun onToDateClicked() {
        binding.toDateFormEditText.setOnClickListener {
            presenter.onToDateClick(binding.toDateFormEditText.text.toString())
        }
    }

    private fun onReturnBinding() {

        arguments?.getParcelable<BookingData>(BookingFormActivity.EXTRA_BOOKING)
            ?.let { bookingData = it }
        with(binding) {
            //valiadate ของ argument ใน presenter ( != null)
            //valiadate ของเดิม
            if (bookingData.validateBookingData()) {
                //TODO: create fun in interface View to setTexts
                //TODO: presenter call fun
                nameFormEditText.setText(bookingData.fullName)
                emailFormEditText.setText(bookingData.email)
                phoneFormEditText.setText(bookingData.phoneNumber)
                roomFormDropdown.setText(bookingData.room, false)
                reasonFormEditText.setText(bookingData.reason)
                fromDateFormEditText.setText(bookingData.fromDate)
                fromTimeFormDropdown.setText(bookingData.fromTime, false)
                toDateFormEditText.setText(bookingData.toDate)
                toTimeFormDropDown.setText(bookingData.toTime, false)
            }
        }
        arguments?.remove(BookingFormActivity.EXTRA_BOOKING)
    }

    private fun setEditTextIsFocus(editText: TextInputEditText, isFocus: Boolean) {
        editText.isFocusableInTouchMode = isFocus
        editText.isFocusable = isFocus
        if (isFocus) {
            editText.requestFocus()
        }
    }
}
