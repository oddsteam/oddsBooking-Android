package com.odds.oddsbooking.presentations.booking.preview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.presentations.booking.BookingFormActivity
import com.odds.oddsbooking.models.BookingData
import com.odds.oddsbooking.databinding.FragmentBookingPreviewBinding
import com.odds.oddsbooking.services.booking.BookingAPIFactory

class BookingPreviewFragment : Fragment(), BookingPreviewView {

    private val binding by lazy { FragmentBookingPreviewBinding.inflate(layoutInflater) }
    private val presenter by lazy {
        BookingPreviewPresenter(
            BookingAPIFactory.createBookingAPI(
                requireContext()
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.getBookingInfo(arguments?.getParcelable(BookingFormActivity.EXTRA_BOOKING)!!)
        binding.confirmButton.setOnClickListener {
            presenter.createBooking()
        }
        binding.backToBookingFormButton.setOnClickListener {
            presenter.backToBookingFormPage()
        }
    }

    override fun setAllEditTextFromBookingData(bookingData: BookingData) {
        val fromDate = "${bookingData.fromDate} ${bookingData.fromTime}"
        val toDate = "${bookingData.toDate} ${bookingData.toTime}"
        with(binding) {
            namePreviewEditText.setText(bookingData.fullName)
            emailPreviewEditText.setText(bookingData.email)
            phonePreviewEditText.setText(bookingData.phoneNumber)
            roomPreviewEditText.setText(bookingData.room)
            reasonPreviewEditText.setText(bookingData.reason)
            fromDateTimePreviewEditText.setText(fromDate)
            toDateTimePreviewEditText.setText(toDate)
        }
    }


    override fun showProgressBar() {
        binding.layoutProgressBar.isVisible = true
        binding.confirmButton.isEnabled = false
        binding.backToBookingFormButton.isEnabled = false
    }

    override fun goToSuccessPage(bookingData: BookingData) {
        binding.layoutProgressBar.isGone = true
        findNavController().apply {
            navigate(
                R.id.bookingSuccessFragment,
                bundleOf(
                    BookingFormActivity.EXTRA_BOOKING to bookingData
                )
            )
        }
    }

    override fun backToBookingFormPage() {
        findNavController().popBackStack()
    }


    override fun showToastMessage(errorMessage: String) {
        Toast.makeText(
            requireContext(),
            errorMessage,
            Toast.LENGTH_LONG
        ).show()
    }
}