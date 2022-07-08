package com.odds.oddsbooking.services.booking

import com.google.gson.annotations.SerializedName

data class BookingDetailResponse(
    @SerializedName("id")
    val id: String?,
    @SerializedName("fullName")
    val fullName: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("phoneNumber")
    val phoneNumber: String?,
    @SerializedName("room")
    val room: String?,
    @SerializedName("reason")
    val reason: String?,
    @SerializedName("startDate")
    val startDate: String?,
    @SerializedName("endDate")
    val endDate: String?,
    @SerializedName("status")
    val status: Boolean?,
)

data class BookingResponse(
    @SerializedName("status")
    val status: Number?,
    @SerializedName("data")
    val data : BookingDetailResponse?,
)
