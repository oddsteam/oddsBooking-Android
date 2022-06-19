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
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import com.odds.oddsbooking.interfaces.BookingData
import com.odds.oddsbooking.interfaces.BookingFormView
import com.odds.oddsbooking.presentations.booking.BookingFormActivity
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*


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

    private fun loadRoomDropdown(){
        val rooms = resources.getStringArray(R.array.rooms)
        val arrayAdapter = ArrayAdapter(binding.roomFormDropdown.context, R.layout.dropdown_item, rooms)
        binding.roomFormDropdown.setAdapter(arrayAdapter)
    }

    private fun showDatePickerDialog(
        editText: com.google.android.material.textfield.TextInputEditText,
        minDate: Long,
        maxDate: Long?
    ) {
        editText.isFocusableInTouchMode = true;
        editText.isFocusable = true;
        editText.requestFocus()

        val calendar = Calendar.getInstance()
        var years = calendar.get(Calendar.YEAR)
        var months = calendar.get(Calendar.MONTH)
        var days = calendar.get(Calendar.DAY_OF_MONTH)

        if(editText.text.toString().isNotEmpty()) {
            val dates = editText.text.toString().split("/")
            years = dates[0].toInt()
            months = dates[1].toInt() -1
            days = dates[2].toInt()
        }

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = String.format("%d/%02d/%02d", year, month + 1, day)
            editText.setText(date)
            editText.isFocusableInTouchMode = false
            editText.isFocusable = false
        }

        val listenerDismiss = DialogInterface.OnDismissListener {
            editText.isFocusableInTouchMode = false
            editText.isFocusable = false
        }

        val listenerCancel = DialogInterface.OnCancelListener {
            editText.isFocusableInTouchMode = false
            editText.isFocusable = false
        }

        val dpd = DatePickerDialog.newInstance(
            listener,
            years,
            months,
            days
        )
        dpd.setOnDismissListener(listenerDismiss)
        dpd.setOnCancelListener(listenerCancel)

        calendar.timeInMillis = minDate
        dpd.minDate = calendar
        if (maxDate != null) {
            calendar.timeInMillis = maxDate
            dpd.maxDate = calendar
        }
        dpd.setTitle("Select ${editText.hint}")
        dpd.show(childFragmentManager, "DatePickerDialog")
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

    override fun onValidateFromDateSuccess(timeSlot: Array<String>, minDate: Long, maxDate: Long) {
        with(binding) {
            fromDateFormContainer.isErrorEnabled = false
            fromDateFormContainer.error = null

            toDateFormEditText.setOnClickListener {
                showDatePickerDialog(binding.toDateFormEditText, minDate, maxDate)
            }

            fromTimeFormDropdown.isEnabled = true
            fromTimeFormContainer.setBoxBackgroundColorResource(enable)
            toDateFormContainer.isEnabled = false
            toDateFormContainer.setBoxBackgroundColorResource(disable)
            fromDateFormEditText.text?.let { toDateFormEditText.text = it  }

            val fromTime: Array<String> = timeSlot
            val arrayAdapterFromTime =
                ArrayAdapter(binding.fromTimeFormDropdown.context, R.layout.dropdown_item, fromTime)
            val autocompleteTVFromTime =
                binding.root.findViewById<AutoCompleteTextView>(R.id.fromTimeFormDropdown)
            autocompleteTVFromTime.setAdapter(arrayAdapterFromTime)

            if (!fromTimeFormDropdown.text.isNullOrEmpty()) {
                fromTimeFormDropdown.setText("")
            }
            toTimeFormContainer.setBoxBackgroundColorResource(disable)
            toTimeFormDropDown.isEnabled = false
            if (!toTimeFormDropDown.text.isNullOrEmpty()) {
                toTimeFormDropDown.setText("")
            }
        }
    }

    override fun onValidateFromTimeError(errMsg: String) {
        val container = binding.fromTimeFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateFromTimeSuccess(timeSlot: Array<String>) {
        with(binding) {
            fromTimeFormContainer.isErrorEnabled = false
            fromTimeFormContainer.error = null

            setTimeDropdown(timeSlot, binding.toTimeFormDropDown)

            toDateFormContainer.isEnabled = true
            toDateFormContainer.setBoxBackgroundColorResource(enable)

            toTimeFormDropDown.isEnabled = false
            toTimeFormContainer.setBoxBackgroundColorResource(disable)

            toTimeFormDropDown.isEnabled = true
            toTimeFormContainer.setBoxBackgroundColorResource(enable)
            if (!toTimeFormDropDown.text.isNullOrEmpty()) {
                toTimeFormDropDown.setText("")
            }
        }
    }

    override fun onValidateToDateError(errMsg: String) {
        val container = binding.toDateFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateToDateSuccess(timeSlot: Array<String>) {
        with(binding) {
            toDateFormContainer.isErrorEnabled = false
            toDateFormContainer.error = null
            toTimeFormDropDown.isEnabled = true
            toTimeFormContainer.setBoxBackgroundColorResource(enable)

            setTimeDropdown(timeSlot,binding.toTimeFormDropDown)

            if (!toTimeFormDropDown.text.isNullOrEmpty()) {
                toTimeFormDropDown.setText("")
            }
        }
    }

    override fun onValidateToTimeError(errMsg: String) {
        val container = binding.toTimeFormContainer
        container.isErrorEnabled = true
        container.error = errMsg
    }

    override fun onValidateToTimeSuccess() {
        val container = binding.toTimeFormContainer
        container.isErrorEnabled = false
        container.error = null
    }


    override fun onNameAutoFormat(name: String) {
        val nameFormatter = name.lowercase().trim().split("\\s+".toRegex()).toMutableList()
        for (index in nameFormatter.indices) {
            nameFormatter[index] = nameFormatter[index].replaceFirstChar { it.uppercaseChar() }
        }
        binding.nameFormEditText.setText(nameFormatter.joinToString(" "))
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

    private fun setTimeDropdown(timeSlot: Array<String>, view: View){
        val toTime: Array<String> = timeSlot
        val arrayAdapterToTime = ArrayAdapter(view.context, R.layout.dropdown_item, toTime)
        val dropdown = binding.root.findViewById<AutoCompleteTextView>(view.id)
        dropdown.setAdapter(arrayAdapterToTime)

    }

    private fun formValueValidate(){
        with(binding){
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

    private fun formValidate(){
        with(binding){
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

    private fun dateTimePickerEnable(){
        with(binding){
            fromTimeFormDropdown.isEnabled = fromDateFormEditText.text?.isNotEmpty() == true
            toTimeFormDropDown.isEnabled = fromDateFormEditText.text?.isNotEmpty() == true
            toDateFormEditText.isEnabled = fromDateFormEditText.text?.isNotEmpty() == true
        }
    }

    private fun onPreviewButtonClick(){
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
            showDatePickerDialog(
                binding.fromDateFormEditText,
                System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
                null
            )
        }
    }


    private fun onReturnBinding() {
        arguments?.getParcelable<BookingData>(BookingFormActivity.EXTRA_BOOKING)?.let { bookingData = it  }
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

    companion object {}
}
