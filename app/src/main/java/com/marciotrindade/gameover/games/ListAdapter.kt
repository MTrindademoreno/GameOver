package com.marciotrindade.gameover.games

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marciotrindade.gameover.databinding.ItemGameListBinding
import kotlinx.coroutines.withContext

class ListAdapter(private val listGame :List<GameItem>,
                  private val clickItem:(Int) -> Unit):RecyclerView.Adapter<ListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameListBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.bind(listGame[position],clickItem)
    }

    override fun getItemCount(): Int {
       return listGame.size
    }

    class ViewHolder(private val binding: ItemGameListBinding):RecyclerView.ViewHolder(binding.root) {
        fun bind(game: GameItem, clickItem: (Int) -> Unit)= with(binding)  {

            tvTitleGame.text = game.name
            tvDateGame.text = game.date
            val img= game.image

            Glide.with(itemView.context).load(img).into(imgItemGame)

            binding.root.setOnClickListener {
                clickItem(this@ViewHolder.adapterPosition)
            }


        }

    }
}