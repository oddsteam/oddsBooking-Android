package com.odds.oddsbooking.services.booking

import com.google.gson.annotations.SerializedName

data class BookingResponse(
    @SerializedName("id")
    val id: String,
)
