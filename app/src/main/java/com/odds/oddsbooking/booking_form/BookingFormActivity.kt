package com.odds.oddsbooking.booking_form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.ActivityBookingFormBinding

class BookingFormActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBookingFormBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        // get reference to the string array that we just created
        val rooms = resources.getStringArray(R.array.rooms)
        // create an array adapter and pass the required parameter
        // in our case pass the context, drop down layout , and array.
        val arrayAdapter = ArrayAdapter(this, R.layout.dropdown_item, rooms)
        // get reference to the autocomplete text view
        val autocompleteTV = findViewById<AutoCompleteTextView>(R.id.dropdown)
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
    }
}