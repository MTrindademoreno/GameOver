package com.marciotrindade.gameover.addGame

import android.content.Context
import android.net.Uri

class AddGameBusiness(private val context: Context
) {

    private val repository by lazy {
        AddGameRepository(context)
    }

    suspend fun saveImage(selectedPhotoUri: Uri):GameAddResponse{
        return repository.saveImage(selectedPhotoUri )
    }

    suspend fun saveGameInFirebase(url:String, name:String, date:String, description:String):GameAddResponse {
       return repository.saveGameInFirebase(url,name,date,description)
    }

    suspend fun updateGameInFirebase( name: String, date: String, description: String):GameAddResponse{
        return repository.updateGameInFirebase(name,date,description)
    }
}