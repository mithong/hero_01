package com.sun.hero_01.ui.skin

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sun.hero_01.R
import com.sun.hero_01.data.model.HeroSkin
import com.sun.hero_01.utils.ImageType
import com.sun.hero_01.utils.extensions.loadHeroImage
import kotlinx.android.synthetic.main.item_skin.view.*

class HeroSkinAdapter(val idHero: String?) : RecyclerView.Adapter<HeroSkinAdapter.ViewHolder?>() {

    private val skins = mutableListOf<HeroSkin>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_skin, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount() = skins.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(skins[position])

    fun updateData(skins: MutableList<HeroSkin>?) {
        skins?.let {
            this.skins.clear()
            this.skins.addAll(it)
            notifyDataSetChanged()
        }
    }

    inner class ViewHolder(
        itemView: View
    ) : RecyclerView.ViewHolder(itemView) {

        fun bind(skin: HeroSkin) {
            with(itemView) {
                imageHeroSkin.loadHeroImage("${idHero}_${skin.num}.jpg", ImageType.SKIN)
            }
        }
    }
}
