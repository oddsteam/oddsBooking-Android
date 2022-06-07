package com.odds.oddsbooking.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.odds.oddsbooking.booking_form.BookingFormActivity
import com.odds.oddsbooking.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers
import okhttp3.Dispatcher

class SplashActivity : AppCompatActivity(), SplashPresenter.SplashView {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    private val presenter by lazy { SplashPresenter(Dispatchers.Main,2000) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        presenter.attachView(this)
        presenter.splashing()
    }

    override fun goToBookingForm() {
        val intent = Intent(this, BookingFormActivity::class.java)
        startActivity(intent)
    }
}
