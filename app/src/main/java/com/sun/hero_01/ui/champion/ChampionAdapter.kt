package com.sun.hero_01.ui.champion

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.hero_01.R
import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.utils.Constant
import com.sun.hero_01.utils.HeroDifficulty
import com.sun.hero_01.utils.LoadImageBitmap
import kotlinx.android.synthetic.main.item_layout_hero.view.*

class ChampionAdapter : RecyclerView.Adapter<ChampionAdapter.ViewHolder>() {

    private val heroes = mutableListOf<Hero>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_hero, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = heroes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindViewData(heroes[position])
    }

    fun updateData(heroes: MutableList<Hero>?) {
        heroes?.let {
            this.heroes.clear()
            this.heroes.addAll(it)
            notifyDataSetChanged()
        }
    }

    fun getDifficulty(level: Int?): String {
        return when (level) {
            in HeroDifficulty.EASY.diff -> HeroDifficulty.EASY.nameDiff
            in HeroDifficulty.AVERAGE.diff -> HeroDifficulty.AVERAGE.nameDiff
            in HeroDifficulty.HARD.diff -> HeroDifficulty.HARD.nameDiff
            in HeroDifficulty.SEVERE.diff -> HeroDifficulty.SEVERE.nameDiff
            else -> ""
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindViewData(hero: Hero) {
            itemView.apply {
                textViewHeroName.text = hero.name
                textViewHeroNickname.text = hero.title
                textViewHeroDifficulty.text = getDifficulty(hero.difficulty)
                getImage(hero)
            }
        }

        private fun getImage(hero: Hero) {
            LoadImageBitmap(itemView.imageViewHero)
                .execute("${Constant.BASE_URL}/${Constant.BASE_VERSION}/${Constant.PATH_IMAGE_CHAMPION}/${hero.image}")
        }
    }
}
