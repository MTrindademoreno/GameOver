package com.marciotrindade.gameover.utils

import android.content.Context
import android.widget.EditText
import com.google.android.material.textfield.TextInputLayout
import com.marciotrindade.gameover.R

class Validation(val context: Context) {


    fun isEditTextFilled(et: EditText, til: TextInputLayout, label: String): Boolean {
        return if (et.text.isNotEmpty()) {
            til.isErrorEnabled = false
            true
        } else {
            til.error = context.getString(R.string.field_required, label)
            et.requestFocus()
            false
        }
    }

    fun isEmailValid(et: EditText, til: TextInputLayout): Boolean {
        return when {
            android.util.Patterns.EMAIL_ADDRESS.matcher(et.text.toString()).matches() -> {
                til.isErrorEnabled = false
                true
            }
            else -> {
                if (et.text.isEmpty()) {
                    til.error = context.getString(
                        R.string.field_required,
                        context.getString(R.string.email)
                    )
                } else {
                    til.error = context.getString(R.string.msg_invalid_email)
                }
                et.requestFocus()
                false
            }
        }
    }

    fun isPasswordsEquals(et1: EditText, et2: EditText, til2: TextInputLayout): Boolean {
        return if (et1.text.toString() == et2.text.toString()) {
            til2.isErrorEnabled = false
            true
        } else {
            til2.error = context.getString(R.string.password_doesnt_match)
            et2.requestFocus()
            false
        }
    }

}