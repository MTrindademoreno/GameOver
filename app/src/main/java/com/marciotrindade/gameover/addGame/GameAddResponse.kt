package com.marciotrindade.gameover.addGame

import com.google.firebase.firestore.QuerySnapshot

sealed class GameAddResponse {
    class Success(val result: String): GameAddResponse()
    class Error(val message:String): GameAddResponse()
}