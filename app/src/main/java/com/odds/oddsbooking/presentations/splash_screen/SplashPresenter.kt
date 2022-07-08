package com.odds.oddsbooking.presentations.splash_screen

import kotlinx.coroutines.*

//TODO: edit splash screen
class SplashPresenter constructor(
    private val dispatcher: CoroutineDispatcher,
    private val duration: Long
) {
    private lateinit var view: SplashView
    private val scope = CoroutineScope(Job() + dispatcher)

    fun attachView(view : SplashView){
        this.view = view
    }

    fun splashing(){
        view.showAnimation()
        scope.launch {
            delay(duration)
            view.goToBookingForm()
        }
    }
}