package com.marciotrindade.gameover.auth

import com.google.firebase.auth.FirebaseUser
import com.google.firebase.storage.FirebaseStorage
import com.marciotrindade.gameover.model.User

class AuthBusiness {
    private val repository by lazy {
        AuthRepository()

    }


    suspend fun signIn(email:String,password:String):FirebaseUser? {
        return repository.signIn(email, password)

    }
    suspend fun createUserInFirebase(email: String,password: String):FirebaseUser?{
        return repository.createUser(email,password)
    }

    suspend fun saveUserInFirebase(user: User):Boolean {
        return repository.saveUserInFirebase(user)


    }

}