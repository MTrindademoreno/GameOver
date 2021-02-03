package com.marciotrindade.gameover.addGame

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class AddGameViewModel(application: Application):AndroidViewModel(application) {
    val urlLiveData:MutableLiveData<String> = MutableLiveData()
    val erro:MutableLiveData<String> = MutableLiveData()
    val message:MutableLiveData<String> = MutableLiveData()

    private val business by lazy {
        AddGameBusiness(application)
    }

    fun saveImage(photoUri: Uri){

        viewModelScope.launch {
            when(val response = business.saveImage(photoUri)){
                is GameAddResponse.Success ->{
                    urlLiveData.postValue(response.result)

                }
                is GameAddResponse.Error->{
                    erro.postValue(response.message)

                }
            }


        }

    }

    fun saveGameInFirebase(url:String,name:String,date:String,description:String) {
        viewModelScope.launch {

            when(val response =business.saveGameInFirebase(url,name,date,description)){
                is GameAddResponse.Success->{
                    message.postValue(response.result)
                }
                is GameAddResponse.Error->{
                    message.postValue(response.message)
                }
            }

        }

    }

     fun upDateGameInFirebase( name: String, date: String, description: String) {

         viewModelScope.launch {
             when(val response =business.updateGameInFirebase(name,date,description)){
                 is GameAddResponse.Success->{
                     message.postValue(response.result)
                 }
                 is GameAddResponse.Error->{
                     message.postValue(response.message)
                 }
             }
         }


    }
}