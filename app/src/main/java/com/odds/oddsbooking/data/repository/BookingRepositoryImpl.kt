package com.odds.oddsbooking.data.repository

import com.odds.oddsbooking.models.BookingRequest
import com.odds.oddsbooking.data.api.BookingApi
import com.odds.oddsbooking.models.BookingResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

//TODO: write test
class BookingRepositoryImpl @Inject constructor(private val api: BookingApi) : BookingRepository{

    override fun createBooking(request: BookingRequest): Flow<BookingResponse> {
        return  flow{
            val response = api.createBooking(request)
            if (response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                    //emit -> return ของออกไป แต่ส่งไปตาม Flow ซึ่งสามารถส่งไปได้เรื่อยๆ เหมือนโยนของเข้าท่อทีละชิ้น
                }
            } else {
                throw Exception("response not success")
            }
        }
    }
}