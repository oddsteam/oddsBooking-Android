package com.odds.oddsbooking.booking_form

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.odds.oddsbooking.R
import com.odds.oddsbooking.databinding.FragmentBookingPreviewBinding
import com.odds.oddsbooking.services.booking.BookingAPIFactory

class BookingPreviewFragment : Fragment(), BookingPreviewPresenter.BookingPreviewView {

    private val binding by lazy { FragmentBookingPreviewBinding.inflate(layoutInflater) }
    private val presenter by lazy {
        BookingPreviewPresenter(
            BookingAPIFactory.createBookingAPI(
                requireContext()
            )
        )
    }
    private lateinit var bookingData: BookingData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter.attachView(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val bookingInfo = bind()
        binding.confirmButton.setOnClickListener {
            presenter.createBooking(bookingInfo)
        }
        binding.backToBookingFormButton.setOnClickListener {
            presenter.backToBookingFormPage()
        }
        return binding.root
    }

    private fun bind(): Booking {
        bookingData = arguments?.getParcelable(BookingFormActivity.EXTRA_BOOKING)!!
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
        return Booking(
            bookingData.fullName,
            bookingData.email,
            bookingData.phoneNumber,
            bookingData.room,
            bookingData.reason,
            "${dateTimeGeneralFormat(bookingData.fromDate)}T${bookingData.fromTime}",
            "${dateTimeGeneralFormat(bookingData.toDate)}T${bookingData.toTime}",
            false
        )
    }

    private fun dateTimeGeneralFormat(dateTime: String): String {
        val (day, month, year) = dateTime.split("-").toTypedArray()
        return "${year}-${month}-${day}"
    }

    override fun goToSuccessPage() {
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
        findNavController().apply {
            navigate(
                R.id.bookingFormFragment,
                bundleOf(
                    BookingFormActivity.EXTRA_BOOKING to bookingData
                )
            )
        }
    }

    override fun showToastMessage(errorMessage: String) {
        Toast.makeText(
            requireContext(),
            errorMessage,
            Toast.LENGTH_LONG
        ).show()
    }
}