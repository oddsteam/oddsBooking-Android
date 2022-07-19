package com.odds.oddsbooking.di

import com.odds.oddsbooking.data.repository.BookingRepository
import com.odds.oddsbooking.data.repository.BookingRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class DataModule {
    @Binds
    abstract fun bindBookingRepository(bookingRepositoryImpl: BookingRepositoryImpl): BookingRepository
}

//@Binds
//  abstract fun bindAnalyticsService(
//    analyticsServiceImpl: AnalyticsServiceImpl
//  ): AnalyticsService