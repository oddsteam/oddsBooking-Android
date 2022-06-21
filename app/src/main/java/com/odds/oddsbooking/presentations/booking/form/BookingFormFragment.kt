package com.odds.oddsbooking.presentations.booking.form

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
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
import com.odds.oddsbooking.models.FromDate
import com.odds.oddsbooking.models.ToDate
import com.odds.oddsbooking.presentations.booking.BookingFormActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog


class BookingFormFragment : Fragment(), BookingFormView {

    private val binding by lazy { FragmentBookingFormBinding.inflate(layoutInflater) }

    private val presenter by lazy {
        BookingFormPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    private var bookingData: BookingData = BookingData()
    private val disable = R.color.disable_color
    private val enable = android.R.color.transparent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loadRoomDropdown()
        formValueValidate()
        formValidate()
        dateTimePickerEnable()
        onFromDateClicked()
        onPreviewButtonClick()
        onReturnBinding()
    }

    private fun loadRoomDropdown() {
        val rooms = resources.getStringArray(R.array.rooms)
        val arrayAdapter =
            ArrayAdapter(binding.roomFormDropdown.context, R.layout.dropdown_item, rooms)
        binding.roomFormDropdown.setAdapter(arrayAdapter)
    }

    private fun showDatePickerDialog(
        editText: TextInputEditText,
        minDate: Long,
        maxDate: Long?
    ) {
        //TODO: move focus to other func after & before date picker with flag by presenter
        val calendarDate = presenter.getCurrentCalendar(editText.text.toString())
        val listener = onDateSetListener(editText)
        val listenerDismiss = onDismissListener(editText)
        val listenerCancel = onCancelListener(editText)
        val dpd = DatePickerDialog.newInstance(
            listener,
            calendarDate.years,
            calendarDate.months,
            calendarDate.days
        )
        dpd.setOnDismissListener(listenerDismiss)
        dpd.setOnCancelListener(listenerCancel)
        calendarDate.calendar.timeInMillis = minDate
        dpd.minDate = calendarDate.calendar
        if (maxDate != null) {
            calendarDate.calendar.timeInMillis = maxDate
            dpd.maxDate = calendarDate.calendar
        }
        dpd.setTitle("Select ${editText.hint}")
        dpd.show(childFragmentManager, "DatePickerDialog")
    }

    private fun onCancelListener(editText: TextInputEditText) =
        DialogInterface.OnCancelListener {
            setEditTextIsFocus(editText, false)
        }

    private fun onDismissListener(editText: TextInputEditText) =
        DialogInterface.OnDismissListener {
            setEditTextIsFocus(editText, false)
        }

    private fun onDateSetListener(editText: TextInputEditText) =
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = presenter.getDateFormatter(year, month, day)
            editText.setText(date)
            setEditTextIsFocus(editText, false)
        }

    override fun onValidateNameError(errMsg: String) {
        val container = binding.nameFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateNameSuccess() {
        val container = binding.nameFormContainer
        container.isErrorEnabled = false
        container.error = null
    }

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

    override fun onValidateFromDateError(errMsg: String) {
        val container = binding.fromDateFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateFromDateSuccess(timeSlot: Array<String>) {

        with(binding) {
            removeErrorContainer(fromDateFormContainer)
            onToDateClicked()
            setFromTimeEnable(true, enable)
            setToDateEnable(false, disable)
            setToTimeEnable(false, disable)

            fromDateFormEditText.text?.let { toDateFormEditText.text = it }

            setTimeDropdown(timeSlot, fromTimeFormDropdown)
            setDropDownWithValueToEmpty(fromTimeFormDropdown)
            setDropDownWithValueToEmpty(toTimeFormDropDown)
        }
    }

    private fun setFromTimeEnable(isEnable: Boolean, backgroundColor: Int){
        binding.fromTimeFormDropdown.isEnabled = isEnable
        binding.fromTimeFormContainer.setBoxBackgroundColorResource(backgroundColor)
    }

    private fun setToDateEnable(isEnable: Boolean, backgroundColor: Int){
        binding.toDateFormContainer.isEnabled = isEnable
        binding.toDateFormContainer.setBoxBackgroundColorResource(backgroundColor)
    }

    private fun setToTimeEnable(isEnable: Boolean, backgroundColor: Int){
        binding.toTimeFormDropDown.isEnabled = isEnable
        binding.toTimeFormContainer.setBoxBackgroundColorResource(backgroundColor)
    }

    private fun setDropDownWithValueToEmpty(editText: AutoCompleteTextView) {
        if (!editText.text.isNullOrEmpty()) {
            editText.setText("")
        }
    }

    override fun onValidateFromTimeError(errMsg: String) {
        val container = binding.fromTimeFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateFromTimeSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(fromTimeFormContainer)

            setTimeDropdown(timeSlot, binding.toTimeFormDropDown)

            setToDateEnable(true, enable)
            setToTimeEnable(true, enable)

            setDropDownWithValueToEmpty(toTimeFormDropDown)
        }
    }

    override fun onValidateToDateError(errMsg: String) {
        val container = binding.toDateFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateToDateSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(toDateFormContainer)

            setToTimeEnable(true, enable)

            setTimeDropdown(timeSlot, binding.toTimeFormDropDown)

            setDropDownWithValueToEmpty(toTimeFormDropDown)
        }
    }

    override fun onValidateToTimeError(errMsg: String) {
        val container = binding.toTimeFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateToTimeSuccess() {
        removeErrorContainer(binding.toTimeFormContainer)
    }

    override fun onDatePickerDialogFormDate(fromDate: FromDate) {
        showDatePickerDialog(
            binding.fromDateFormEditText,
            fromDate.minDate,
            fromDate.maxDate
        )
    }

    override fun onDatePickerDialogToDate(toDate: ToDate) {
        showDatePickerDialog(
            binding.toDateFormEditText,
            toDate.minDate,
            toDate.maxDate
        )
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

    private fun formValueValidate() {
        with(binding) {
            nameFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateFullName(text.toString())
                nameFormEditText.setOnFocusChangeListener { _, _ -> presenter.autoFormatName(text.toString()) }
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
                presenter.validateToDate(text.toString(), fromDateFormEditText.text.toString(), fromTimeFormDropdown.text.toString())
            }

            toTimeFormDropDown.doOnTextChanged { text, _, _, _ ->
                presenter.validateToTime(text.toString())
            }
        }
    }

    private fun formValidate() {
        with(binding) {
            nameFormEditText.doAfterTextChanged { text ->
                bookingData.fullName = text.toString()
                presenter.validateForm()
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
            setEditTextIsFocus(binding.fromDateFormEditText, true)
            presenter.onFromDateClick()
        }
    }

    private fun onToDateClicked() {
        binding.toDateFormEditText.setOnClickListener {
            setEditTextIsFocus(binding.toDateFormEditText, true)
            presenter.onToDateClick()
        }
    }

    private fun onReturnBinding() {
        arguments?.getParcelable<BookingData>(BookingFormActivity.EXTRA_BOOKING)
            ?.let { bookingData = it }
        with(binding) {
            if (bookingData.validateBookingData()) {
                nameFormEditText.setText(bookingData.fullName)
                emailFormEditText.setText(bookingData.email)
                phoneFormEditText.setText(bookingData.phoneNumber)
                roomFormDropdown.setText(bookingData.room, false)
                reasonFormEditText.setText(bookingData.reason)
                fromDateFormEditText.setText(bookingData.fromDate)
                fromTimeFormDropdown.setText(bookingData.fromTime)
                toDateFormEditText.setText(bookingData.toDate)
                toTimeFormDropDown.setText(bookingData.toTime)
            }
        }
    }

    private fun setEditTextIsFocus(editText: TextInputEditText, isFocus: Boolean){
        editText.isFocusableInTouchMode = isFocus
        editText.isFocusable = isFocus
        if (isFocus) {
            editText.requestFocus()
        }
    }
}
