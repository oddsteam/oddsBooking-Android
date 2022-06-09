package com.odds.oddsbooking.services.booking

import com.odds.oddsbooking.booking_form.Booking
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface BookingAPI {
    @POST("/v1/booking")
    @Headers("Content-Type: application/json")
    suspend fun createBooking(
        @Body booking: Booking
    ) : Response<BookingResponse>
}