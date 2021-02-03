package com.marciotrindade.gameover.listGame

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marciotrindade.gameover.model.GameItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class ListViewModel:ViewModel() {
    val business = ListBusiness()
    val listGames:MutableLiveData<MutableList<GameItem>> = MutableLiveData()
    val erro:MutableLiveData<String> = MutableLiveData()

    fun getCollection(){

       viewModelScope.launch {
           when(val response = business.getCollection()){
               is GameResponse.Success ->{
                   val lista = response.result.toObjects(GameItem::class.java)
                   listGames.postValue(lista)
               }
               is GameResponse.Error ->{

                   erro.postValue(
                   response.message)
               }
           }
       }


    }
}