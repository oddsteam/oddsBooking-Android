package com.odds.oddsbooking

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.odds.oddsbooking.booking_form.BookingFormActivity
import com.odds.oddsbooking.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        delay(afterDelay = {
            Intent(this, BookingFormActivity::class.java).apply {
                val flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                addFlags(flags)
                startActivity(this)
            }
        })

    }

    private fun delay(beforeDelay: () -> Unit = {}, afterDelay: () -> Unit) {
        beforeDelay()
        Handler(Looper.getMainLooper()).postDelayed({
            afterDelay()
        }, 3000)
    }
}
