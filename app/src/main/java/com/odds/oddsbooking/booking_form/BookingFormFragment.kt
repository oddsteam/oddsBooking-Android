package com.odds.oddsbooking.booking_form

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import java.util.*

class BookingFormFragment : Fragment() , BookingFormPresenter.BookingFormView{

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

//        dropdown room list (All start & Neon)
        val rooms = resources.getStringArray(R.array.rooms)
        val arrayAdapter =
            ArrayAdapter(binding.roomFormDropdown.context, R.layout.dropdown_item, rooms)
        binding.roomFormDropdown.setAdapter(arrayAdapter)

//        onClick date
        binding.fromDateFormEditText.setOnClickListener {
            showDatePickerDialog(binding.fromDateFormEditText)
        }

        binding.toDateFormEditText.setOnClickListener {
            showDatePickerDialog(binding.toDateFormEditText)
        }

//        onClick previewButton
        binding.previewButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.bookingPreviewFragment
                )
            }
        }

        with(binding) {
            nameFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateName(text.toString())
            }
            emailFormEditText.doOnTextChanged { text, _, _, _ ->
                presenter.validateEmail(text.toString())
            }
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

    override fun onEmailError(errMsg: String) {
        val emailFormContainer = binding.emailFormContainer
        emailFormContainer.isErrorEnabled = true
        //        TODO: change String type to int & declaration @String
        emailFormContainer.error = errMsg
    }
    override fun onEmailValid(){
        binding.emailFormContainer.isErrorEnabled = false
    }


    companion object {
//        send EXTRA
    }

    // TODO : Implement Date Picker Dialog with minDate and maxDate
    private fun showDatePickerDialog(
        editText: com.google.android.material.textfield.TextInputEditText,
        minDate: Long = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 14),
        maxDate: Long = System.currentTimeMillis() + (1000 * 60 * 60 * 24 * 15)
    ) {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val listener = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            val date = String.format("%02d-%02d-%d", day, month, year)
            editText.setText(date)
        }

        val dialog = DatePickerDialog(
            requireContext(),
            listener,
            year,
            month,
            day
        )
        dialog.datePicker.minDate = minDate
        dialog.datePicker.maxDate = maxDate
        dialog.show()
    }
}