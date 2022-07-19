package com.odds.oddsbooking.data.api

import com.odds.oddsbooking.models.BookingRequest
import com.odds.oddsbooking.models.BookingResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BookingApi {
    @POST("/v1/booking")
    @Headers("Content-Type: application/json")
    suspend fun createBooking(
        @Body bookingRequest: BookingRequest
    ) : Response<BookingResponse>
}