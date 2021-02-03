package com.marciotrindade.gameover.listGame

import android.net.Uri
import com.google.firebase.firestore.QuerySnapshot

sealed class GameResponse{
    class Success(val result:QuerySnapshot):GameResponse()
    class Error(val message:String):GameResponse()
}
