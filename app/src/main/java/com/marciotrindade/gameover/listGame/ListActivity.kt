package com.marciotrindade.gameover.listGame

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.load.data.mediastore.MediaStoreUtil
import com.marciotrindade.gameover.R
import com.marciotrindade.gameover.addGame.AddGameActivity
import com.marciotrindade.gameover.databinding.ActivityListBinding
import com.marciotrindade.gameover.detailGame.DetailActivity
import com.marciotrindade.gameover.model.GameItem

class ListActivity : AppCompatActivity() {
    private lateinit var listAdapter: ListAdapter
    private lateinit var binding: ActivityListBinding
    private lateinit var viewModel: ListViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this).get(ListViewModel::class.java)



    }

    override fun onResume() {
        super.onResume()


        viewModel.getCollection()

        initMenu()

        viewModel.listGames.observe(this, Observer{
            setupRecyclerview(it as ArrayList<GameItem>)
        })


        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddGameActivity::class.java)
            startActivity(intent)

            // apagar a coleção e refatorar os a classe Game Item e as propriedades


        }

        binding.search.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                this@ListActivity.listAdapter.filter.filter(newText)
                return false
            }

        })



    }



    private fun setupRecyclerview(lista:ArrayList<GameItem>) {
        binding.rvListFragment.apply {
            layoutManager = GridLayoutManager(this@ListActivity, 2)
            listAdapter= ListAdapter(lista) {position ->
                val game = lista[position]
                val intent = Intent(this@ListActivity, DetailActivity::class.java)
                intent.putExtra(GAME,game)
                startActivity(intent)
            }
            adapter=listAdapter




        }
    }

    private fun initMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_btn_voice -> {
                    displaySpeechRecognizer()
                    Toast.makeText(this, "voice", Toast.LENGTH_SHORT).show()





                    true
                }
                else -> false
            }
        }


    }
    companion object{
        const val GAME = "game"
        private const val SPEECH_REQUEST_CODE = 0
    }




    private fun displaySpeechRecognizer() {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
            putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
        }

        startActivityForResult(intent, SPEECH_REQUEST_CODE)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == SPEECH_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val spokenText: String? =
                data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).let { results ->
                    results?.get(0)
                }
           //Verificar código
        }
        super.onActivityResult(requestCode, resultCode, data)
    }


}