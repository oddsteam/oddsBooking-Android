package com.odds.oddsbooking.booking_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.core.os.bundleOf
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

    private val editTextArray by lazy {
        arrayOf<FormValidate>(
            FormValidate(
                "name",
                binding.nameFormContainer,
                binding.nameFormEditText,
                "error name empty",
                arrayOf(presenter::isEmpty)
            ),
            FormValidate(
                "email",
                binding.emailFormContainer,
                binding.emailFormEditText,
                "error email",
                arrayOf(presenter::isEmpty, presenter::isEmail)
            ),
            FormValidate(
                "phone",
                binding.phoneFormContainer,
                binding.phoneFormEditText,
                "error phone empty",
                arrayOf(presenter::isEmpty, presenter::isPhone)
            ),
            FormValidate(
                "reason",
                binding.reasonFormContainer,
                binding.reasonFormEditText,
                "error reason empty",
                arrayOf(presenter::isEmpty)
            ),
            FormValidate(
                "startDate",
                binding.fromDateFormContainer,
                binding.fromDateFormEditText,
                "error from date empty",
                arrayOf(presenter::isEmpty)
            ),
            FormValidate(
                "startTime",
                binding.fromTimeFormContainer,
                binding.fromTimeFormEditText,
                "error from time empty",
                arrayOf(presenter::isEmpty)
            ),
            FormValidate(
                "endDate",
                binding.toDateFormContainer,
                binding.toDateFormEditText,
                "error to date empty",
                arrayOf(presenter::isEmpty)
            ),
            FormValidate(
                "endTime",
                binding.toTimeFormContainer,
                binding.toTimeFormEditText,
                "error from date empty",
                arrayOf(presenter::isEmpty)
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    private lateinit var bookingData: BookingData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val rooms = resources.getStringArray(R.array.rooms)
        val arrayAdapter =
            ArrayAdapter(binding.roomFormDropdown.context, R.layout.dropdown_item, rooms)
        val autocompleteTV = binding.root.findViewById<AutoCompleteTextView>(R.id.roomFormDropdown)
        autocompleteTV.setAdapter(arrayAdapter)

        editTextArray.forEach {
            it.fieldBindingEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validate(text.toString(), it.tagName, it.chains)
                if (it.tagName == "name")
                    it.fieldBindingEditText.setOnFocusChangeListener { _, _ ->
                        presenter.autoFormatName(text.toString())
                    }
                when (it.tagName) {
                    "name" -> {
                        bookingData.fullName = text.toString()
                    }
                    "phone" -> {
                        bookingData.phoneNumber = text.toString()
                    }
                    "email" -> {
                        bookingData.email = text.toString()
                    }
                    "reason" -> {
                        bookingData.reason = text.toString()
                    }
                    "startDate" -> {
                        bookingData.fromDate = text.toString()
                    }
                    "startTime" -> {
                        bookingData.fromTime = text.toString()
                    }
                    "endDate" -> {
                        bookingData.toDate = text.toString()
                    }
                    "endTime" -> {
                        bookingData.toTime = text.toString()
                    }
                }
            }
        }

        with(binding) {

            roomFormDropdown.doOnTextChanged { text, _, _, _ ->
                presenter.validateRoom(text.toString())
            }
            // showDialog date/time picker
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

            // onClick previewButton
            previewButton.setOnClickListener {
                findNavController().apply {
                    navigate(
                        R.id.bookingPreviewFragment,
                        bundleOf(
                            BookingFormActivity.EXTRA_BOOKING to bookingData
                        )
                    )
                }
            }

            nameFormEditText.addTextChangedListener(textWatcher())
            emailFormEditText.addTextChangedListener(textWatcher())
            phoneFormEditText.addTextChangedListener(textWatcher())
            roomFormDropdown.addTextChangedListener(textWatcher())
            reasonFormEditText.addTextChangedListener(textWatcher())
            fromDateFormEditText.addTextChangedListener(textWatcher())
            fromTimeFormEditText.addTextChangedListener(textWatcher())
            toDateFormEditText.addTextChangedListener(textWatcher())
            toTimeFormEditText.addTextChangedListener(textWatcher())
        }

        bind()

        return binding.root
    }

    override fun onNameAutoFormat(name: String) {
        val nameFormatter = name.lowercase().trim().split("\\s+".toRegex()).toMutableList()
        for (index in nameFormatter.indices) {
            nameFormatter[index] = nameFormatter[index].replaceFirstChar { it.uppercaseChar() }
        }
        binding.nameFormEditText.setText(nameFormatter.joinToString(" "))
    }

    override fun onRoomError(errMsg: String) {
        val roomFormContainer = binding.roomFormContainer
        roomFormContainer.isErrorEnabled = true
        roomFormContainer.error = errMsg
    }

    override fun onRoomValid() {
        binding.roomFormContainer.isErrorEnabled = false
        bookingData.room = binding.roomFormDropdown.text.toString()
    }

    override fun onError(tagName: String) {
        val form = editTextArray.find {
            tagName == it.tagName
        }
        val container = form?.fieldBindingContainer
        container?.isErrorEnabled = true
        container?.error = form?.errMsg
    }

    override fun onValid(tagName: String) {
        val form = editTextArray.find {
            tagName == it.tagName
        }
        val container = form?.fieldBindingContainer
        container?.isErrorEnabled = false
    }

    override fun onErrorMessage(tagName: String, errMsg: String) {
        val form = editTextArray.find {
            tagName == it.tagName
        }
        val container = form?.fieldBindingContainer
        container?.isErrorEnabled = true
        container?.error = errMsg
    }

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

    private fun textWatcher() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            if (bookingData.isValid()) {
                var numberOfValid = 0
                editTextArray.forEach {
                    if (!it.fieldBindingContainer.isErrorEnabled)
                        numberOfValid++
                    Log.d(
                        "bookingData",
                        "${it.tagName} : ${it.fieldBindingContainer.isErrorEnabled}"
                    )
                }
                if (!binding.roomFormContainer.isErrorEnabled)
                    numberOfValid++
                Log.d(
                    "bookingData",
                    "room : ${binding.roomFormContainer.isErrorEnabled}"
                )
                if (numberOfValid == 9) {
                    enablePreviewButton()
                } else {
                    disablePreviewButton()
                }
                Log.d("bookingData", "numberOfValid: $numberOfValid")
            } else {
                disablePreviewButton()
            }
            Log.d("bookingData", bookingData.toString())
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        }

        fun enablePreviewButton() {
            binding.previewButton.isEnabled = true
            binding.previewButton.setBackgroundColor(
                resources.getColor(
                    R.color.purple_color,
                    null
                )
            )
        }

        fun disablePreviewButton() {
            binding.previewButton.isEnabled = false
            binding.previewButton.setBackgroundColor(
                resources.getColor(
                    R.color.gray_outline,
                    null
                )
            )
        }
    }

    private fun bind() {
        bookingData = arguments?.getParcelable(BookingFormActivity.EXTRA_BOOKING)!!
        with(binding) {
            if (bookingData.fullName.isNotEmpty())
                nameFormEditText.setText(bookingData.fullName)
            if (bookingData.email.isNotEmpty())
                emailFormEditText.setText(bookingData.email)
            if (bookingData.phoneNumber.isNotEmpty())
                phoneFormEditText.setText(bookingData.phoneNumber)
            if (bookingData.room.isNotEmpty())
                roomFormDropdown.setText(bookingData.room, false)
            if (bookingData.reason.isNotEmpty())
                reasonFormEditText.setText(bookingData.reason)
            if (bookingData.fromDate.isNotEmpty())
                fromDateFormEditText.setText(bookingData.fromDate)
            if (bookingData.fromTime.isNotEmpty())
                fromTimeFormEditText.setText(bookingData.fromTime)
            if (bookingData.toDate.isNotEmpty())
                toDateFormEditText.setText(bookingData.toDate)
            if (bookingData.toTime.isNotEmpty())
                toTimeFormEditText.setText(bookingData.toTime)
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(bookingData: BookingData): BookingFormFragment {
            return BookingFormFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BookingFormActivity.EXTRA_BOOKING, bookingData)
                }
            }
        }
    }
}
