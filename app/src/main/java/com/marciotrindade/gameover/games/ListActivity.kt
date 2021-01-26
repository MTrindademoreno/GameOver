package com.marciotrindade.gameover.games

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.marciotrindade.gameover.R
import com.marciotrindade.gameover.databinding.ActivityListBinding

class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initMenu()
        setupRecyclerview()



    }

    private fun setupRecyclerview() {
        binding.rvListFragment.apply {
            layoutManager = GridLayoutManager(this@ListActivity, 2)
//dados moc
            val lista = listOf(
                GameItem(
                    "jogo1",
                    "1999",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo5feSozPnUB-dKriE7I5q2hsSX71QbWmS2A&usqp=CAU",
                    ""
                ),
                GameItem(
                    "jogo 2",
                    "2011",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
                    ""
                ),
                GameItem(
                    "jogo 2",
                    "2011",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
                    ""
                ),
                GameItem(
                    "jogo 2",
                    "2011",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
                    ""
                ),
                GameItem(
                    "jogo 2",
                    "2011",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
                    description
                ),
                GameItem(
                    "jogo 2",
                    "2011",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
                   description
                ),
                GameItem(
                    "jogo 2",
                    "2011",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTEPvlBR4Hf5CqSX1-oqrtpu20VGf0FdeKUGw&usqp=CAU",
                    ""
                ),
                GameItem(
                    "jogo1",
                    "1999",
                    "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSo5feSozPnUB-dKriE7I5q2hsSX71QbWmS2A&usqp=CAU",
                    description
                )
            )
            adapter = ListAdapter(lista) {position ->
                val game = lista[position]
                val intent = Intent(this@ListActivity, DetailActivity::class.java)
                intent.putExtra(GAME,game)
                startActivity(intent)
            }
        }
    }

    private fun initMenu() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.item_btn_voice -> {

                    Toast.makeText(this, "voice", Toast.LENGTH_SHORT).show()





                    true
                }
                else -> false
            }
        }


    }
    companion object{
        const val GAME = "game"
    }

    //descriçao moc
    val description ="Mortal Kombat é uma série de jogos criados pelo estúdio de Chicago da Midway Games. Em 2011, depois da falência da Midway, a produção de Mortal Kombat foi adquirida pela Warner Bros, tornando-se em seguida na Netherealm. A Warner detém actualmente os direitos da série.\n" +
            "\n" +
            "A produção do primeiro jogo foi baseada na ideia original que Ed Boon e John Tobias tinham em fazer um jogo em que participasse Jean-Claude Van Damme,[2] mas a ideia foi deixada de parte, e em vez disso foi criado Mortal Kombat, um jogo de luta com temas de fantasia e ciência, " +
            "lançado em Outubro de 1992.[3] O jogo original, gerou muitas sequelas, vários jogos de acção-aventura, filmes (animados e live-action com a sua sequela) e séries de televisão (animadas e live-action). Outra média inclui banda desenhada, jogo de cartas e a Mortal Kombat: Live Tour," +
            " um teatro de artes marciais com personagens da série. "
}