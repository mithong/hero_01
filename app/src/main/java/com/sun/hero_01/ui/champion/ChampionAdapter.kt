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
import com.sun.hero_01.utils.OnItemRecyclerViewListener
import kotlinx.android.synthetic.main.item_layout_hero.view.*

class ChampionAdapter(
    private val onItemClickListener: OnItemRecyclerViewListener<Hero>?,
    private val onItemLongClick: (String?, View?) -> Unit
) : RecyclerView.Adapter<ChampionAdapter.ViewHolder>() {

    private val heroes = mutableListOf<Hero>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_layout_hero, parent, false)
        return ViewHolder(view, onItemClickListener, onItemLongClick)
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

    fun getDifficulty(level: Int?): ArrayList<Any> {
        return when (level) {
            in HeroDifficulty.EASY.diff -> arrayListOf(
                HeroDifficulty.EASY.nameDiff,
                R.drawable.ic_easy
            )
            in HeroDifficulty.AVERAGE.diff -> arrayListOf(
                HeroDifficulty.AVERAGE.nameDiff,
                R.drawable.ic_average
            )
            in HeroDifficulty.HARD.diff -> arrayListOf(
                HeroDifficulty.HARD.nameDiff,
                R.drawable.ic_hard
            )
            in HeroDifficulty.SEVERE.diff -> arrayListOf(
                HeroDifficulty.SEVERE.nameDiff,
                R.drawable.ic_severe
            )
            else -> arrayListOf("", 0)
        }
    }

    inner class ViewHolder(
        itemView: View,
        private val itemListener: OnItemRecyclerViewListener<Hero>?,
        private val onItemLongClick: (String?, View?) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private var listener: OnItemRecyclerViewListener<Hero>? = null

        init {
            itemView.setOnClickListener(this@ViewHolder)
        }

        override fun onClick(v: View?) {
            listener?.onItemClickListener(heroes[adapterPosition])
        }

        fun bindViewData(hero: Hero) = with(itemView) {
            textViewHeroName.text = hero.name
            textViewHeroNickname.text = hero.title
            textViewHeroDifficulty.text = getDifficulty(hero.difficulty)[0].toString()
            imageViewCircle.setImageResource(getDifficulty(hero.difficulty)[1] as Int)
            listener = itemListener
            setOnLongClickListener {
                onItemLongClick(hero.name, this)
                true
            }
            getImage(hero)
        }

        private fun getImage(hero: Hero) {
            LoadImageBitmap(itemView.imageViewHero)
                .execute("${Constant.BASE_URL}/${Constant.BASE_VERSION}/${Constant.PATH_IMAGE_CHAMPION}/${hero.image}")
        }
    }
}
