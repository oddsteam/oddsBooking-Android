package com.odds.oddsbooking.booking_form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import com.odds.oddsbooking.databinding.FragmentBookingPreviewBinding

class BookingPreviewFragment : Fragment() {

    private val binding by lazy { FragmentBookingPreviewBinding.inflate(layoutInflater) }

    private lateinit var bookingData: BookingData

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        bind()

        binding.backToBookingFormButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.bookingFormFragment,
                    bundleOf(
                        BookingFormActivity.EXTRA_BOOKING to bookingData
                    )
                )
            }

        }

        binding.confirmButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.bookingSuccessFragment,
                    bundleOf(
                        BookingFormActivity.EXTRA_BOOKING to bookingData
                    )
                )
            }
        }

        return binding.root
    }

    private fun bind() {
        bookingData = arguments?.getParcelable(BookingFormActivity.EXTRA_BOOKING)!!
        with(binding) {
            namePreviewEditText.setText(bookingData.fullName)
            emailPreviewEditText.setText(bookingData.email)
            phonePreviewEditText.setText(bookingData.phoneNumber)
            roomPreviewEditText.setText(bookingData.room)
            reasonPreviewEditText.setText(bookingData.reason)
            fromDateTimePreviewEditText.setText("${bookingData.fromDate}T${bookingData.fromTime}")
            toDateTimePreviewEditText.setText("${bookingData.toDate}T${bookingData.toTime}")
        }
    }

    companion object {

        @JvmStatic
        fun newInstance(bookingData: BookingData): BookingPreviewFragment {
            return BookingPreviewFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(BookingFormActivity.EXTRA_BOOKING, bookingData)
                }
            }
        }
    }
}