package com.marciotrindade.gameover.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameItem(
    val name:String?,
    val date : String?,
    val image: String?,
    val description:String?,
    val id :String?
):Parcelable
{

    constructor() : this(null,null,null,null,null)
}