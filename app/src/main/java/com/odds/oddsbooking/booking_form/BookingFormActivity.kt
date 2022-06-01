package com.odds.oddsbooking.booking_form

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.odds.oddsbooking.databinding.ActivityBookingFormBinding

class BookingFormActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBookingFormBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}