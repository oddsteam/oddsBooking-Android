package com.odds.oddsbooking.booking_form

import android.util.Patterns


class BookingFormPresenter {
    private lateinit var view: BookingFormView

    fun attachView(view: BookingFormView) {
        this.view = view
    }

    interface BookingFormView {
        fun onNameAutoFormat(name: String)
        fun onRoomError(errMsg: String)
        fun onRoomValid()
        fun onError(tagName: String)
        fun onValid(tagName: String)
        fun onErrorMessage(tagName: String, errMsg: String)
    }

    fun validate(
        value: String,
        tagName: String,
        chain: Array<(value: String, tagName: String) -> Boolean>
    ) {
        when {
            chain.isNotEmpty() -> {
                chain.forEach {
                    val err = it(value, tagName)
                    if (err) return@forEach
                }
            }
            else -> {
                view.onValid(tagName)
            }
        }
    }

    fun isEmpty(value: String, tagName: String): Boolean {
        when {
            value.isEmpty() -> {
                view.onError(tagName)
                return true
            }
            else -> {
                view.onValid(tagName)
            }
        }
        return false
    }

    fun isEmail(value: String, tagName: String): Boolean {
        when {
            !Patterns.EMAIL_ADDRESS.matcher(value).matches() -> {
                view.onErrorMessage(tagName, "format email error")
                return true
            }
            else -> {
                view.onValid(tagName)
            }
        }
        return false
    }

    fun isPhone(value: String, tagName: String): Boolean {
        when {
            !Regex("^0[9, 8, 6, 2][0-9]{8}\$").matches(value) -> {
                view.onErrorMessage(tagName, "format phone number error")
                return true
            }
            else -> {
                view.onValid(tagName)
            }
        }
        return false
    }

    fun autoFormatName(name: String) {
        view.onNameAutoFormat(name)
    }


    fun validateRoom(room: String) {
        when {
            room.isEmpty() -> {
                view.onRoomError("Please enter room")

            }
            else -> {
                view.onRoomValid()
            }
        }
    }

}