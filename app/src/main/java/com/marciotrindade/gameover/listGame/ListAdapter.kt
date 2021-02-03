package com.marciotrindade.gameover.listGame

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.marciotrindade.gameover.databinding.ItemGameListBinding
import com.marciotrindade.gameover.model.GameItem
import java.util.*
import kotlin.collections.ArrayList

class ListAdapter(private val listGame :ArrayList<GameItem>,
                  private val clickItem:(Int) -> Unit):RecyclerView.Adapter<ListAdapter.ViewHolder>(),Filterable {
    private var gameFilter = ArrayList<GameItem>()

    init {
        gameFilter = listGame
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemGameListBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
     holder.bind(gameFilter[position],clickItem)
    }

    override fun getItemCount(): Int {
       return gameFilter.size
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

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    gameFilter = listGame
                } else {
                    val resultList = ArrayList<GameItem>()
                    for (game in listGame) {

                        if (game.name?.toLowerCase(Locale.ROOT)?.contains(charSearch.toLowerCase(
                                Locale.ROOT)) == true
                        ) {
                            resultList.add(game)
                        }
                    }
                    gameFilter = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = gameFilter
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {




                notifyDataSetChanged()


            }

        }
    }
}