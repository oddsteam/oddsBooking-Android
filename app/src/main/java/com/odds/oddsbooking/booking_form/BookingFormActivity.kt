package com.odds.oddsbooking.booking_form

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.navigation.fragment.NavHostFragment
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.ActivityBookingFormBinding
import java.util.*

class BookingFormActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBookingFormBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val nav = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = nav.navController
        navController.setGraph(R.navigation.oddsbooking_navigation)
    }

}