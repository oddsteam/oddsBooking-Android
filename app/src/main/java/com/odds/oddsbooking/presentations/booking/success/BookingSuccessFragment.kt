package com.odds.oddsbooking.presentations.booking.success

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import com.odds.oddsbooking.presentations.booking.BookingFormActivity
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.databinding.FragmentBookingSuccessBinding

class BookingSuccessFragment : Fragment() {

    private val binding by lazy { FragmentBookingSuccessBinding.inflate(layoutInflater) }

    private val viewModel: BookingSuccessViewModel by viewModels()
    private var bookingData: BookingData? = BookingData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        observe()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        addCallbackOnBackPressedDispatcher{
            viewModel.onReturnToForm()
        }
        bookingData = arguments?.getParcelable(BookingFormActivity.EXTRA_BOOKING)

        binding.bookingAgainButton.setOnClickListener {
            viewModel.onReturnToForm()
        }
        return binding.root
    }

    private fun addCallbackOnBackPressedDispatcher(onBackPressed: () -> Unit = {}) {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
        )
    }

    private fun onReturnToForm () {
        activity?.finish()
        Intent(requireContext(), BookingFormActivity::class.java).apply {
            startActivity(this)
        }
    }

    private fun observe() {
        viewModel.onReturnToForm.observe(this) { onReturnToForm() }
    }
}