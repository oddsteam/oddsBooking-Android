package com.odds.oddsbooking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.odds.oddsbooking.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private val binding by lazy { ActivitySplashBinding.inflate(layoutInflater) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}