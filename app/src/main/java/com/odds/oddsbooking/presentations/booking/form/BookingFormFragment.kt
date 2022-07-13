package com.odds.oddsbooking.presentations.booking.form

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
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.models.DateInTimePicker
import com.odds.oddsbooking.presentations.booking.BookingFormActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog


class BookingFormFragment : Fragment(), BookingFormView {

    private val binding by lazy { FragmentBookingFormBinding.inflate(layoutInflater) }

    private val presenter by lazy { BookingFormPresenter() }
    private val viewModel : BookingFormViewModel by viewModels()
    private val disable = R.color.disable_color
    private val enable = android.R.color.transparent

    //region Fragment Life Cycle
    override fun onResume() {
        super.onResume()
        viewModel.setFromTimesDropDown()
        viewModel.setToTimesDropDown()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }
    private fun observe(){
        viewModel.setFromTimesDropDown.observe(this){
            setFromTimeDropdown(it)
        }
        viewModel.setToTimesDropDown.observe(this){
            setToTimeDropDown(it)
        }
        viewModel.enablePreviewButton.observe(this){
            enablePreviewButton()
        }
        viewModel.disablePreviewButton.observe(this){
            disablePreviewButton()
        }

        viewModel.onValidateNameSuccess.observe(this){
            onValidateEmailSuccess()
        }
        viewModel.onValidateNameError.observe(this){
            onValidateNameError(it)
        }
        viewModel.onNameAutoFormat.observe(this){
            onNameAutoFormat(it)
        }

        viewModel.onValidateEmailSuccess.observe(this){
            onValidateEmailSuccess()
        }
        viewModel.onValidateEmailError.observe(this){
            onValidateEmailError(it)
        }
        viewModel.onValidatePhoneNumberSuccess.observe(this){
            onValidatePhoneNumberSuccess()
        }
        viewModel.onValidatePhoneNumberError.observe(this){
            onValidatePhoneNumberError(it)
        }
        viewModel.onValidateRoomError.observe(this){
            onValidateRoomError(it)
        }
        viewModel.onValidateRoomSuccess.observe(this){
            onValidateRoomSuccess()
        }
        viewModel.onValidateReasonError.observe(this){
            onValidateReasonError(it)
        }
        viewModel.onValidateReasonSuccess.observe(this){
            onValidateReasonSuccess()
        }

        viewModel.onValidateFromDateSuccess.observe(this){
            onValidateFromDateSuccess(it)
        }
        viewModel.onValidateFromDateError.observe(this){
            onValidateFromDateError(it)
        }

        viewModel.onValidateToDateSuccess.observe(this){
            onValidateToDateSuccess(it)
        }
        viewModel.onValidateToDateError.observe(this){
            onValidateToDateError(it)
        }

        viewModel.setFromTimeDropdown.observe(this){
            setFromTimeDropdown(it)
        }
        viewModel.setToTimeDropDown.observe(this){
            setToTimeDropDown(it)
        }
        viewModel.clearValueFromTimeDropdown.observe(this){
            clearValueFromTimeDropdown()
        }
        viewModel.clearValueToTimeDropdown.observe(this){
            clearValueToTimeDropdown()
        }
        viewModel.setEnableFromTime.observe(this){
            setEnableFromTime()
        }
        viewModel.setEnableToTime.observe(this){
            setEnableToTime()
        }
        viewModel.setDisableToDate.observe(this){
            setDisableToDate()
        }
        viewModel.setDisableToTime.observe(this){
            setDisableToTime()
        }

        viewModel.onDatePickerDialogFormDate.observe(this){
            onDatePickerDialogFormDate(it)
        }
        viewModel.onDatePickerDialogToDate.observe(this){
            onDatePickerDialogToDate(it)
        }

        viewModel.setDisableFromDateEditText.observe(this){
            setDisableFromDateEditText()
        }
        viewModel.setDisableToDateEditText.observe(this){
            setDisableToDateEditText()
        }
        viewModel.setTextFromDate.observe(this){
            setTextFromDate(it)
        }
        viewModel.setTextToDate.observe(this){
            setTextToDate(it)
        }
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
        formValidate()
        dateTimePickerEnable()
        onFromDateClicked()
        onToDateClicked()
        onPreviewButtonClick()
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
            viewModel.onDatePickerCancel()
        }

    private fun onDismissListener() =
        DialogInterface.OnDismissListener {
            viewModel.onDatePickerDismiss()
        }

    private fun onDateSetListener() = //confirm
        DatePickerDialog.OnDateSetListener { _, year, month, day ->
            viewModel.onDatePickerConfirm(year, month, day)
        }
    //endregion

    //TODO change presenter >> viewModel
    //region formValidates
    private fun formValidate() {
        with(binding) {
            nameFormEditText.doAfterTextChanged { text ->
                viewModel.validateFullName(text.toString())
                nameFormEditText.setOnFocusChangeListener { _, _ -> viewModel.autoFormatName(text.toString()) }
            }

            emailFormEditText.doAfterTextChanged { text ->
                viewModel.validateEmail(text.toString())
            }

            phoneFormEditText.doAfterTextChanged { text ->
                viewModel.validatePhoneNumber(text.toString())
            }

            roomFormDropdown.doAfterTextChanged { text ->
                viewModel.validateRoom(text.toString())
            }

            reasonFormEditText.doAfterTextChanged { text ->
                viewModel.validateReason(text.toString())
            }

            fromDateFormEditText.doAfterTextChanged { text ->
                viewModel.validateFromDate(text.toString())
            }

            fromTimeFormDropdown.doAfterTextChanged { text ->
                presenter.validateFromTime(
                    text.toString(),
                    fromDateFormEditText.text.toString(),
                    toDateFormEditText.text.toString(),
                )
            }

            toDateFormEditText.doAfterTextChanged { text ->
                viewModel.validateToDate(
                    text.toString(),
                    fromDateFormEditText.text.toString(),
                    fromTimeFormDropdown.text.toString()
                )
            }

            toTimeFormDropDown.doAfterTextChanged { text ->
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
    override fun onValidateEmailError(errMsg: Int) {
        val container = binding.emailFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidateEmailSuccess() {
        val container = binding.emailFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate PhoneNumber
    override fun onValidatePhoneNumberError(errMsg: Int) {
        val container = binding.phoneFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidatePhoneNumberSuccess() {
        val container = binding.phoneFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate Room
    override fun onValidateRoomError(errMsg: Int) {
        val container = binding.roomFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidateRoomSuccess() {
        val container = binding.roomFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate Reason
    override fun onValidateReasonError(errMsg: Int) {
        val container = binding.reasonFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidateReasonSuccess() {
        val container = binding.reasonFormContainer
        container.isErrorEnabled = false
        container.error = null
    }
    //endregion

    //region validate FromDate
    override fun onValidateFromDateError(errMsg: Int) {
        val container = binding.fromDateFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidateFromDateSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(fromDateFormContainer)

            fromDateFormEditText.text?.let { toDateFormEditText.text = it }
        }
    }
    //endregion

    //region validate FromTime
    override fun onValidateFromTimeError(errMsg: Int) {
        val container = binding.fromTimeFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidateFromTimeSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(fromTimeFormContainer)
        }
    }
    //endregion

    //region validate ToDate
    override fun onValidateToDateError(errMsg: Int) {
        val container = binding.toDateFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
    }

    override fun onValidateToDateSuccess(timeSlot: Array<String>) {
        with(binding) {
            removeErrorContainer(toDateFormContainer)
        }
    }
    //endregion

    //region validate ToTime
    override fun onValidateToTimeError(errMsg: Int) {
        val container = binding.toTimeFormContainer
        container.isErrorEnabled = true
        container.error = getString(errMsg)
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
        binding.nameFormEditText.setText(name)
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
            presenter.onPreviewButtonClicked()
        }
    }

    override fun onNavigateToPreview(bookingData: BookingData) {
        findNavController().apply {
            navigate(
                R.id.bookingPreviewFragment,
                bundleOf(
                    BookingFormActivity.EXTRA_BOOKING to bookingData
                )
            )
        }
    }

    private fun onFromDateClicked() {
        binding.fromDateFormEditText.setOnClickListener {
            viewModel.onFromDateClick(binding.fromDateFormEditText.text.toString())
        }
    }

    private fun onToDateClicked() {
        binding.toDateFormEditText.setOnClickListener {
            viewModel.onToDateClick(
                binding.toDateFormEditText.text.toString(),
                binding.fromDateFormEditText.text.toString())
        }
//        binding.toDateFormEditText.setOnClickListener(object : View.OnClickListener {
//            override fun onClick(p0: View?) {
//                presenter.onToDateClick(
//                binding.toDateFormEditText.text.toString(),
//                binding.fromDateFormEditText.text.toString())
//            }
//        } )
    }

    private fun setEditTextIsFocus(editText: TextInputEditText, isFocus: Boolean) {
        editText.isFocusableInTouchMode = isFocus
        editText.isFocusable = isFocus
        if (isFocus) {
            editText.requestFocus()
        }
    }
}
