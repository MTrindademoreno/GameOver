package com.marciotrindade.gameover.auth

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseUser
import com.marciotrindade.gameover.model.User
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val business = AuthBusiness()

    val authenticationState: MutableLiveData<FirebaseUser?> = MutableLiveData()
    val createUserState: MutableLiveData<FirebaseUser?> = MutableLiveData()


     fun signIn(email: String, password: String) {

         viewModelScope.launch {
             try {
                 business.signIn(email, password)?.let {
                     authenticationState.postValue(
                         it
                     )
                 } ?: run {
                     authenticationState.postValue(
                         null
                     )
                 }
             } catch (e: FirebaseAuthException) {
                 authenticationState.postValue(
                     null
                 )
             }
         }

     }
    fun createUser(email: String, password: String) {

         viewModelScope.launch {
             try {
                 business.createUserInFirebase(email, password)?.let {
                     createUserState.postValue(
                         it
                     )
                 } ?: run {
                     createUserState.postValue(
                         null
                     )
                 }
             } catch (e: FirebaseAuthException) {
                 createUserState.postValue(
                     null
                 )
             }
         }

     }

   fun saveUserInFirebase(user:User) {
//realizar validações
       viewModelScope.launch {
                      business.saveUserInFirebase(user)
       }




    }
}