package com.odds.oddsbooking.booking_form

import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

data class FormValidate(
    val tagName: String,
    val fieldBindingContainer: TextInputLayout,
    val fieldBindingEditText: TextInputEditText,
    val errMsg: String,
    val chains: Array<(value: String, tagName:String) -> Boolean> = arrayOf(),
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FormValidate

        if (!chains.contentEquals(other.chains)) return false

        return true
    }

    override fun hashCode(): Int {
        return chains.contentHashCode()
    }
}