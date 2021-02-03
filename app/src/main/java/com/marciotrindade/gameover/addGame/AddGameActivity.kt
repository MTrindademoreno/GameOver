package com.marciotrindade.gameover.addGame

import android.content.Intent
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.marciotrindade.gameover.databinding.ActivityAddGameBinding
import com.marciotrindade.gameover.listGame.ListActivity.Companion.GAME
import com.marciotrindade.gameover.model.GameItem
import kotlin.properties.Delegates

//mvvm ok
class AddGameActivity : AppCompatActivity() {

    private var gameTitle: String? = null
    private var gameDate: String? = null
    private var gameDescription: String? = null
    private var img: String? = null

    private var verify: Int = 1
    private lateinit var viewModel: AddGameViewModel
    private var selectedPhotoUri: Uri? = null
    private lateinit var binding: ActivityAddGameBinding
    private var game: GameItem? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(AddGameViewModel::class.java)


        setupObservables()

        game = intent.getParcelableExtra(GAME)
        binding.imgBack.setOnClickListener {
            selectPhoto()
        }


        if (game == null) {
            verify = 1
            binding.btnSaveGame.setOnClickListener {

                saveImage()
            }
             Toast.makeText(this, "Adicionar game", Toast.LENGTH_LONG).show()
        } else {
            verify = 0
            insertGameUi()

            binding.btnSaveGame.setOnClickListener {
                val name = binding.edtTitleAddGame.editableText.toString()
                val date = binding.edtDateAddGame.editableText.toString()
                val description = binding.edtDescriptionAddGame.editableText.toString()
                viewModel.upDateGameInFirebase(name, date, description)

            }
            Toast.makeText(this, "Editar Game", Toast.LENGTH_LONG).show()
        }
    }


    private fun setupObservables() {
        viewModel.urlLiveData.observe(this, Observer { url ->
            val name = binding.edtTitleAddGame.editableText.toString()
            val date = binding.edtDateAddGame.editableText.toString()
            val description = binding.edtDescriptionAddGame.editableText.toString()


            if (verify == 0) {
                Toast.makeText(this, "update", Toast.LENGTH_LONG).show()
               //viewModel.upDateGameInFirebase(url, name, date, description)
            } else {
                Toast.makeText(this, "new", Toast.LENGTH_LONG).show()
                viewModel.saveGameInFirebase(url, name, date, description)
            }

        })

        viewModel.message.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
            finish()
        })
        viewModel.erro.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }




    private fun saveImage() {
        selectedPhotoUri?.let { PhotoUri -> viewModel.saveImage(PhotoUri) }
    }

    private fun insertGameUi() {
        gameTitle = game?.name
        gameDate = game?.date
        gameDescription = game?.description
        img = game?.image
        binding.edtDateAddGame.setText(gameDate)
        binding.edtTitleAddGame.setText(gameTitle)
        binding.edtDescriptionAddGame.setText(gameDescription)
        Glide.with(applicationContext).load(img).into(binding.imgPhoto)

    }

    private fun selectPhoto() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, 0)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 0) {

            selectedPhotoUri = data?.data
            try {
                selectedPhotoUri?.let { uri ->
                    if (Build.VERSION.SDK_INT < 28) {
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            this.contentResolver,
                            selectedPhotoUri
                        )
                        binding.imgPhoto.setImageBitmap(bitmap)
                    } else {
                        val source =
                            ImageDecoder.createSource(this.contentResolver, uri)
                        val bitmap = ImageDecoder.decodeBitmap(source)
                        binding.imgPhoto.setImageBitmap(bitmap)
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }


}

