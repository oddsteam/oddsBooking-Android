package com.odds.oddsbooking.booking_form

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import java.text.SimpleDateFormat
import java.util.*

class BookingFormFragment : Fragment() {

    private val binding by lazy { FragmentBookingFormBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rooms = resources.getStringArray(R.array.rooms)
        val arrayAdapter = ArrayAdapter(binding.roomFormDropdown.context, R.layout.dropdown_item, rooms)
        val autocompleteTV = binding.root.findViewById<AutoCompleteTextView>(R.id.roomFormDropdown)
        autocompleteTV.setAdapter(arrayAdapter)

        binding.fromDateFormEditText.setOnClickListener(View.OnClickListener {
            showDatePickerDialog(binding.fromDateFormEditText)
        })

        binding.toDateFormEditText.setOnClickListener(View.OnClickListener {
            showDatePickerDialog(binding.toDateFormEditText)
        })

        binding.previewButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.navigateToPreviewFragment
                )
            }
        }

        return binding.root
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
            val date = String.format("%02d-%02d-%d", day, month, year);
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

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookingFormFragment().apply {
            }
    }
}