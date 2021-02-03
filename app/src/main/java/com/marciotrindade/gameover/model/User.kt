package com.marciotrindade.gameover.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val nome:String,
    val email: String,
    val uid :String
):Parcelable
