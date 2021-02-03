package com.marciotrindade.gameover.addGame

import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.marciotrindade.gameover.model.GameItem
import com.marciotrindade.gameover.utils.Constants.login.GAME_COLLECTION
import com.marciotrindade.gameover.utils.Constants.login.USER_COLLECTION
import kotlinx.coroutines.tasks.await
import java.util.*

class AddGameRepository(
    private val context: Context
) {


    private val auth by lazy {
        Firebase.auth
    }
    private val storage by lazy {
        Firebase.storage
    }
    private val db by lazy {
        Firebase.firestore
    }


    suspend fun saveImage(
        selectedPhotoUri: Uri
    ): GameAddResponse {

        return try {


            val currentUser = auth.currentUser

            currentUser?.let {
                val filename = UUID.randomUUID().toString()
                val storageRef = storage.reference
                val ref = storageRef.child("images/$filename.jpg")
                val url = ref.putFile(selectedPhotoUri)
                    .await()
                    .storage
                    .downloadUrl
                    .await()
                    .toString()

                GameAddResponse.Success(url)
            } ?: run {
                GameAddResponse.Error("Usuário não Logado!!")

            }
        } catch (e: Exception) {
            GameAddResponse.Error(e.localizedMessage ?: "")
        }

    }


   suspend fun saveGameInFirebase(url: String, name: String,
                           date: String,
                           description: String):GameAddResponse {
        return try {
            val uid = auth.currentUser?.uid
            val filename = UUID.randomUUID().toString()
            val gameUpload = GameItem(name,date,url,description,filename)
            val refCollection = db.collection(USER_COLLECTION)
                .document(uid?:"")
                .collection(GAME_COLLECTION)
            val refDocument = refCollection.document(name)
            refDocument.set(gameUpload)
                .await()
            GameAddResponse.Success("Game $name salvo com sucesso!!")

        }catch (e:Exception){
            GameAddResponse.Error("Erro ao salvar Game")
        }




    }

    suspend fun updateGameInFirebase( name: String, date: String, description: String): GameAddResponse {
        return try {
            val uid = auth.currentUser?.uid
            val refCollection = db.collection(USER_COLLECTION)
                .document(uid?:"")
                .collection(GAME_COLLECTION)
            val refDocument = refCollection.document(name)
            refDocument
                .update( "name",name,
                    "date",date, "description",description
                )
                .await()
            GameAddResponse.Success("Game $name atualizado com sucesso!!")

        }catch (e:Exception){
            GameAddResponse.Error("Erro ao salvar Game")
        }





    }


//        if (currentUser != null) {
//            val filename = UUID.randomUUID().toString()
//            val storageRef = storage.reference
//            val ref = storageRef.child("images/$filename.jpg")
//            selectedPhotoUri?.let { photo ->
//
//                ref.putFile(photo)
//
//                    .addOnSuccessListener {
//                        ref.downloadUrl.addOnSuccessListener { Uri ->
//                            Log.i("Teste", it.toString())
//                            val uid = auth.currentUser?.uid
//                            val url = Uri.toString()
//
//
//                            val gameUpload =GameItem(name,date,url,description,filename)
//
//
//                            val refCollection = db.collection(USER_COLLECTION)
//                                .document(uid?:"")
//                                .collection(GAME_COLLECTION)
//                                val refDocument = refCollection.document(name)
//                                refDocument.set(gameUpload)
//                                .addOnSuccessListener {
//                                    Toast.makeText(context, "agora sim", Toast.LENGTH_LONG).show()
//                                }
//
//
//                        }
//                            .addOnFailureListener {
//                                Log.e("Teste", it.toString())
//                            }
//
//                    }
//
//            }
//
//
//        } else {
//            Toast.makeText(context, "usuário inválido", Toast.LENGTH_LONG).show()
//        }
//
//    }
}