package com.odds.oddsbooking.data.repository

import com.odds.oddsbooking.models.BookingRequest
import com.odds.oddsbooking.services.booking.BookingAPI
import com.odds.oddsbooking.services.booking.BookingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BookingRepositoryImpl(private val api: BookingAPI) : BookingRepository{

    override fun createBooking(request: BookingRequest): Flow<BookingResponse> {
        return  flow{
            val response = api.createBooking(request)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                throw Exception("response not success")
            }
        }
    }
}