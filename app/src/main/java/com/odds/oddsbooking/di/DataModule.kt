package com.odds.oddsbooking.di

import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.data.repository.BookingRepositoryImpl
import com.odds.oddsbooking.services.booking.BookingAPI

object DataModule {
    fun createBookingRepository(api: BookingAPI): BookingRepository {
        return BookingRepositoryImpl(api)
    }
}