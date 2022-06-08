package com.odds.oddsbooking.booking_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import java.util.*

class BookingFormFragment : Fragment(), BookingFormPresenter.BookingFormView {

    private val binding by lazy { FragmentBookingFormBinding.inflate(layoutInflater) }

    private val presenter by lazy {
        BookingFormPresenter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rooms = resources.getStringArray(R.array.rooms)
        val arrayAdapter = ArrayAdapter(binding.roomFormDropdown.context, R.layout.dropdown_item, rooms)
        binding.roomFormDropdown.setAdapter(arrayAdapter)

        with(binding) {
//            presenter check onChange
            nameFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateName(text.toString())
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
            fromTimeFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateFromTime(text.toString())
            }
            toDateFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateToDate(text.toString())
            }
            toTimeFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateToTime(text.toString())
            }

//            showDialog date/time picker
            fromDateFormEditText.setOnClickListener {
                showDatePickerDialog(fromDateFormEditText)
            }

            toDateFormEditText.setOnClickListener {
                showDatePickerDialog(toDateFormEditText)
            }

            fromTimeFormEditText.setOnClickListener {
                showTimePickerDialog(fromTimeFormEditText)
            }

            toTimeFormEditText.setOnClickListener {
                showTimePickerDialog(toTimeFormEditText)
            }

            phoneFormEditText.doOnTextChanged{ text, _, _, _ ->
                phoneValidator(text.toString())
            }

            previewButton.setOnClickListener {
                findNavController().apply {
                    navigate(
                        R.id.bookingPreviewFragment
                    )
                }
            }
        }
    }

    private fun phoneValidator(phone: String) {
        val phoneFormContainer = binding.phoneFormContainer
//        val phoneText = binding.phoneFormEditText.text.toString()
        if (phone.isEmpty()) {
            phoneFormContainer.isErrorEnabled = true
            phoneFormContainer.error = "please enter phone"
        } else if(!(phone.startsWith("08") || phone.startsWith("06") || phone.startsWith("09"))){
            phoneFormContainer.isErrorEnabled = true
            phoneFormContainer.error = "invalid pattern"

        }else if (phone.length != 10) {
            phoneFormContainer.isErrorEnabled = true
            phoneFormContainer.error = "invalid phone"
        } else {
            phoneFormContainer.isErrorEnabled = false
        }
    }

    private fun reasonValidator(reason: String) {
        val reasonFormContainer = binding.reasonFormContainer
        if (reason.isEmpty()) {
            reasonFormContainer.isErrorEnabled = true
            reasonFormContainer.error = "plzzzzzz"
        }
        else {
            reasonFormContainer.isErrorEnabled = false
        }
    }

    override fun onNameError(errMsg: String) {
        val nameFormContainer = binding.nameFormContainer
        nameFormContainer.isErrorEnabled = true
        //        TODO: change String type to int & declaration @String
        nameFormContainer.error = errMsg
    }

    override fun onNameValid() {
        binding.nameFormContainer.isErrorEnabled = false
    }

    override fun onNameAutoFormat(term : String) {
        val nameFormatter = term.lowercase().trim().split(" ").toMutableList()
        for (index in nameFormatter.indices) {
            nameFormatter[index] = nameFormatter[index].replaceFirstChar { it.uppercaseChar() }
        }
        binding.nameFormEditText.setText(nameFormatter.joinToString(" "))
    }

    override fun onEmailError(errMsg: String) {
        val emailFormContainer = binding.emailFormContainer
        emailFormContainer.isErrorEnabled = true
        //        TODO: change String type to int & declaration @String
        emailFormContainer.error = errMsg
    }

    override fun onEmailValid() {
        binding.emailFormContainer.isErrorEnabled = false
    }

    override fun onPhoneError(errMsg: String) {
        val phoneFormContainer = binding.phoneFormContainer
        phoneFormContainer.isErrorEnabled = true
        phoneFormContainer.error = errMsg
    }

    override fun onPhoneValid() {
        binding.phoneFormContainer.isErrorEnabled = false
    }

    override fun onFromDateError(errMsg: String) {
        val fromDateContainer = binding.fromDateFormContainer
        fromDateContainer.isErrorEnabled = true
        fromDateContainer.error = errMsg
    }

    override fun onFromDateValid() {
        binding.fromDateFormContainer.isErrorEnabled = false
    }

    override fun onFromTimeError(errMsg: String) {
        val fromTimeContainer = binding.fromTimeFormContainer
        fromTimeContainer.isErrorEnabled = true
        fromTimeContainer.error = errMsg
    }

    override fun onFromTimeValid() {
        binding.fromTimeFormContainer.isErrorEnabled = false
    }

    override fun onToDateError(errMsg: String) {
        val toDateContainer = binding.toDateFormContainer
        toDateContainer.isErrorEnabled = true
        toDateContainer.error = errMsg

    }

    override fun onToDateValid() {
        binding.toDateFormContainer.isErrorEnabled = false
    }

    override fun onToTimeError(errMsg: String) {
        val toTimeContainer = binding.toTimeFormContainer
        toTimeContainer.isErrorEnabled = true
        toTimeContainer.error = errMsg
    }

    override fun onToTimeValid() {
        binding.toTimeFormContainer.isErrorEnabled = false
    }

    // TODO : Implement Date Picker Dialog with minDate and maxDate
    private fun showDatePickerDialog(
        editText: com.google.android.material.textfield.TextInputEditText,
        minDate: Long = System.currentTimeMillis() + (14 * 24 * 60 * 60 * 1000),
        maxDate: Long = System.currentTimeMillis() + (15 * 24 * 60 * 60 * 1000)
    ) {
        val calendar = Calendar.getInstance()
        val years = calendar.get(Calendar.YEAR)
        val months = calendar.get(Calendar.MONTH)
        val days = calendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = String.format("%02d-%02d-%d", day, month, year)
            editText.setText(date)
        }

        val dialog = DatePickerDialog(
            requireContext(),
            listener,
            years,
            months,
            days
        )
        dialog.datePicker.minDate = minDate
        dialog.datePicker.maxDate = maxDate
        dialog.show()
    }

    // TODO : Implement the library and set selected TimePoint
    private fun showTimePickerDialog(
        editText: com.google.android.material.textfield.TextInputEditText,
    ) {

        val listener = TimePickerDialog.OnTimeSetListener { _, hourOfDay, minute, _ ->
            val time = String.format("%02d:%02d", hourOfDay, minute)
            editText.setText(time)
        }

        val tpd = TimePickerDialog.newInstance(
            listener,
            Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
            Calendar.getInstance().get(Calendar.MINUTE),
            true
        )
        tpd.setTimeInterval(2)
        tpd.show(childFragmentManager, "TimepickerDialog")
    }


    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookingFormFragment().apply {
            }
    }
}