package com.odds.oddsbooking.presentations.booking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.navigation.fragment.NavHostFragment
import com.odds.oddsbooking.R
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.databinding.ActivityBookingFormBinding

class BookingFormActivity : AppCompatActivity() {
    private val binding by lazy { ActivityBookingFormBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val nav = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment
        val navController = nav.navController
        navController.setGraph(
            R.navigation.oddsbooking_navigation,
            bundleOf(EXTRA_BOOKING to BookingData())
        )
    }

    companion object {
        const val EXTRA_BOOKING = "EXTRA_BOOKING"
    }

}