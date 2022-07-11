package com.odds.oddsbooking.presentations.splash_screen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import androidx.activity.viewModels
import com.odds.oddsbooking.R
import com.odds.oddsbooking.presentations.booking.BookingFormActivity
import com.odds.oddsbooking.databinding.ActivitySplashBinding
import kotlinx.coroutines.Dispatchers

class SplashActivity : AppCompatActivity(), SplashView {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    private val presenter by lazy { SplashPresenter(Dispatchers.Main,1200) }

    private val viewModel : SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        observe()

        viewModel.splashing()

//        presenter.attachView(this)
//
//        presenter.splashing()
    }

    private fun observe() {
        viewModel.showAnimation.observe(this) {
            showAnimation()
        }

        viewModel.navigateToBookingForm.observe(this) {
            goToBookingForm()
        }
    }

    override fun goToBookingForm() {
        Intent(this, BookingFormActivity::class.java).apply {
            val flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
            addFlags(flags)
            startActivity(this)
        }
    }

    override fun showAnimation() {
        val nameNTextAnimation = AnimationUtils.loadAnimation(this, R.anim.name_version_fade)

        binding.appName.startAnimation(nameNTextAnimation)
        binding.appVersion.startAnimation(nameNTextAnimation)
    }
}
