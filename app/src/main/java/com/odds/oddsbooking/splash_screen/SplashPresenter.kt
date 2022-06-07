package com.odds.oddsbooking.splash_screen

import kotlinx.coroutines.*
import okhttp3.Dispatcher

class SplashPresenter constructor(
    private val dispatcher: CoroutineDispatcher,
    private val duration: Long
) {
    private lateinit var view: SplashView
    private val scope = CoroutineScope(Job() + dispatcher)

    fun attachView(view : SplashView){
        this.view = view
    }

    interface SplashView{
        fun goToBookingForm()
    }

    fun splashing(){
        scope.launch {
            delay(duration)
            view.goToBookingForm()
        }
    }







}