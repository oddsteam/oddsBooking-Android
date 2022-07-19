package com.odds.oddsbooking.di

import com.odds.oddsbooking.data.api.BookingApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    @Provides
    fun provideBookingApi(retrofit : Retrofit) : BookingApi {
        return retrofit.create(BookingApi::class.java)
    }
}