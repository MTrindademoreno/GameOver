package com.marciotrindade.gameover.listGame

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.ktx.Firebase
import com.marciotrindade.gameover.model.GameItem
import kotlinx.coroutines.tasks.await
import java.lang.Exception

class ListBusiness {

    private val db by lazy {
        Firebase.firestore
    }
    private val auth by lazy {
        Firebase.auth
    }
    private val repository by lazy {
        ListRepository()
    }

    suspend fun getCollection(): GameResponse {

        return when (val response = repository.getCollection()) {


            is GameResponse.Success -> {

                GameResponse.Success(response.result)
            }


            is GameResponse.Error -> {
                GameResponse.Error(response.message)
            }
        }

    }


}
