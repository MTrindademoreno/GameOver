package com.marciotrindade.gameover.games

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameItem(
    val name:String,
    val date : String,
    val image: String,
    val description:String?
):Parcelable
