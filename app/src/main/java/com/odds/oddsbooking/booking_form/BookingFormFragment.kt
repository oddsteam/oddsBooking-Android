package com.odds.oddsbooking.booking_form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import com.odds.oddsbooking.databinding.FragmentSuccessBinding

class BookingFormFragment : Fragment() {

    private val binding by lazy { FragmentBookingFormBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

}