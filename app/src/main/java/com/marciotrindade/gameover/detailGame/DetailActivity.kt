
package com.marciotrindade.gameover.detailGame

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.marciotrindade.gameover.R
import com.marciotrindade.gameover.addGame.AddGameActivity
import com.marciotrindade.gameover.model.GameItem
import com.marciotrindade.gameover.listGame.ListActivity.Companion.GAME

class DetailActivity : AppCompatActivity() {
  //  private lateinit var binding:ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        setContentView(R.layout.activity_detail)
        val itemGame =intent.getParcelableExtra<GameItem>(GAME)


        val img = itemGame?.image
        Glide.with(applicationContext).load(img).into(findViewById(R.id.imgDetailGame))
        findViewById<TextView>(R.id.tvTitleGameDetail).text = itemGame?.name
        findViewById<TextView>(R.id.tvSubTitle).text = itemGame?.name
        findViewById<TextView>(R.id.tvDateGameDetail).text = itemGame?.date
        findViewById<TextView>(R.id.descriptionDetail).text = itemGame?.description
        findViewById<ImageView>(R.id.backBtn).setOnClickListener {
            finish()
        }



        findViewById<FloatingActionButton>(R.id.fabEditAddGame).setOnClickListener {


            val intent = Intent(this, AddGameActivity::class.java)
            intent.putExtra(GAME,itemGame)
            startActivity(intent)
        }


//        itemGame?.let{
//
//                Glide.with(applicationContext).load(img).into(binding.imgDetailGame)
//                binding.tvDateGameDetail.text =it.date
//               binding.tvSubTitle.text = it.name
//                binding.tvTitleGameDetail.text = it.name
//               binding.descriptionDetail.text = it.description
//
//      }

    }
}