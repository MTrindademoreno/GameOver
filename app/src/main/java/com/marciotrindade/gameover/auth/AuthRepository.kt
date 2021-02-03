package com.marciotrindade.gameover.auth

import android.util.Log
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marciotrindade.gameover.model.User
import kotlinx.coroutines.tasks.await


class AuthRepository {
    private val auth by lazy {
        Firebase.auth
    }
    private val db by lazy {
        Firebase.firestore
    }

    suspend fun signIn(
        email: String,
        password: String
    ): FirebaseUser? {
        auth.signInWithEmailAndPassword(
            email, password
        ).await()
        return auth.currentUser ?: throw FirebaseAuthException("erro", "Erro ao logar")
    }

    suspend fun createUser(
        email: String,
        password: String
    ): FirebaseUser? {


        auth.createUserWithEmailAndPassword(
            email, password
        ).await()

        return auth.currentUser ?: throw FirebaseAuthException("erro", "Erro ao logar")
    }

   suspend fun saveUserInFirebase(user:User):Boolean{

       db.collection("users")
           .document(auth.currentUser?.uid ?: "")
           .set(user)
           .await()
       return true





    }


}