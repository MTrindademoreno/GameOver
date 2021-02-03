package com.marciotrindade.gameover.listGame

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.marciotrindade.gameover.model.GameItem
import com.marciotrindade.gameover.utils.Constants.login.GAME_COLLECTION
import com.marciotrindade.gameover.utils.Constants.login.USER_COLLECTION
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ListRepository {

    private val db by lazy {
        Firebase.firestore
    }
    private val auth by lazy {
        Firebase.auth
    }

private var list = mutableListOf<GameItem>()
    suspend fun getCollection():GameResponse {


return try {
    auth.currentUser?.uid?.let {
        val gameResponse = db.collection(USER_COLLECTION)
            .document(it)
            .collection(GAME_COLLECTION)
            .orderBy("name")
            .get()
            .await()

        GameResponse.Success(gameResponse)


    }?: run{
        GameResponse.Error("usuário não logado")

    }

}catch (e:Exception){
    GameResponse.Error(e.localizedMessage?:"")
}

//
//        val lista = mutableListOf(
//                GameItem(
//                    "jogo1",
//                    "1999",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo5feSozPnUB-dKriE7I5q2hsSX71QbWmS2A&usqp=CAU",
//                    "",""
//                ),
//                GameItem(
//                    "jogo 2",
//                    "2011",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
//                    "",""
//                ),
//                GameItem(
//                    "jogo 2",
//                    "2011",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
//                    "",""
//                ),
//                GameItem(
//                    "jogo 2",
//                    "2011",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
//                    "",""
//                ),
//                GameItem(
//                    "jogo 2",
//                    "2011",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
//                   " description",""
//                ),
//                GameItem(
//                    "jogo 2",
//                    "2011",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
//                   "description",""
//                ),
//                GameItem(
//                    "jogo 2",
//                    "2011",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
//                    "",""
//                ),
//                GameItem(
//                    "jogo1",
//                    "1999",
//                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo5feSozPnUB-dKriE7I5q2hsSX71QbWmS2A&usqp=CAU",
//                    "description",""
//                )
//            )
//return lista
    }
}