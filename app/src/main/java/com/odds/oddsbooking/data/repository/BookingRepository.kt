package com.odds.oddsbooking.data.repository

import com.odds.oddsbooking.models.BookingRequest
import com.odds.oddsbooking.models.BookingResponse
import kotlinx.coroutines.flow.Flow


interface BookingRepository {
    fun createBooking(request: BookingRequest) : Flow<BookingResponse>
}