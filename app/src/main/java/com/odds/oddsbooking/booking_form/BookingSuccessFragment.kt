package com.odds.oddsbooking.booking_form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingSuccessBinding


class BookingSuccessFragment : Fragment() {

    private val binding by lazy { FragmentBookingSuccessBinding.inflate(layoutInflater) }

    private lateinit var bookingData: BookingData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bookingData = arguments?.getParcelable(BookingFormActivity.EXTRA_BOOKING)!!

        val dummy = BookingData(
            "Taliw Cute Boy",
            bookingData.email,
            bookingData.phoneNumber,
            "",
            "",
            "",
            "",
            "",
            ""
        )

        binding.bookingAgainButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.bookingFormFragment,
                    bundleOf(
                        BookingFormActivity.EXTRA_BOOKING to dummy
                    )
                )
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(bookingData: BookingData): BookingSuccessFragment {
            return BookingSuccessFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BookingFormActivity.EXTRA_BOOKING, bookingData)
                }
            }
        }
    }
}