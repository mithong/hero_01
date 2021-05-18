package com.sun.hero_01.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.hero_01.R
import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.utils.LoadImageBitmap
import kotlinx.android.synthetic.main.item_layout_favourite.view.*

class FavoriteAdapter(
    private val onItemClick: (String) -> Unit,
    private val onItemDelete: (String, Int) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

    private var favoriteHeros = mutableListOf<Favourite>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_favourite, parent, false)
        return ViewHolder(view, onItemClick, onItemDelete)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(favoriteHeros[position])
    }

    override fun getItemCount() = favoriteHeros.size

    fun updateData(hero: MutableList<Favourite>?) {
        hero?.let {
            favoriteHeros.clear()
            favoriteHeros.addAll(it)
            notifyDataSetChanged()
        }
    }

    class ViewHolder(
        itemView: View,
        private val onItemClick: (String) -> Unit,
        private val onItemDelete: (String, Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView){

        fun bindData(hero: Favourite){
            itemView.apply {
                hero.run {
                    textViewNameFavorite.text = heroName
                    getImage(this)
                    setOnClickListener {
                        onItemClick(heroId)
                    }
                    imageViewLikeFavorite.setOnClickListener {
                        onItemDelete(heroId, adapterPosition)
                    }
                }
            }
        }

        private fun getImage(hero: Favourite) {
            LoadImageBitmap(itemView.imageHeroFavorite).execute(hero.heroUrlImage)
        }
    }
}
