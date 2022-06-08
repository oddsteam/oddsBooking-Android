package com.odds.oddsbooking.booking_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Patterns
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
                binding.fromDateFormContainer,
                binding.fromDateFormEditText,
                "error from date empty",
                arrayOf(presenter::isEmpty)
            ),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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
            }
        }

        with(binding) {

            roomFormDropdown.doOnTextChanged { text, _, _, _ ->
                presenter.validateRoom(text.toString())
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

//            onClick previewButton
            previewButton.setOnClickListener {
                findNavController().apply {
                    navigate(
                        R.id.bookingPreviewFragment
                    )
                }
            }
        }
        return binding.root
    }

    override fun onNameAutoFormat(term : String) {
        val nameFormatter = term.lowercase().trim().split("\\s+".toRegex()).toMutableList()
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