package com.odds.oddsbooking.booking_form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingFormBinding
import com.odds.oddsbooking.databinding.FragmentBookingPreviewBinding

class BookingPreviewFragment : Fragment() {

    private val binding by lazy { FragmentBookingPreviewBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding.backToBookingFormButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.navigateToBookingForm
                )
            }

        }

        binding.confirmButton.setOnClickListener {
            findNavController().apply {
                navigate(
                    R.id.navigateToSuccessFragment
                )
            }
        }

        return binding.root
        //return inflater.inflate(R.layout.fragment_booking_preview, container, false)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookingPreviewFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            BookingPreviewFragment().apply {
            }
    }
}