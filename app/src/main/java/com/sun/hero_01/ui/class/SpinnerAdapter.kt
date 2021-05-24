package com.sun.hero_01.ui.`class`

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.sun.hero_01.R
import com.sun.hero_01.data.model.CategoryHero
import kotlinx.android.synthetic.main.item_layout_class.view.*

class SpinnerAdapter(context: Context, listHeroClass: MutableList<CategoryHero>) :
    ArrayAdapter<CategoryHero>(context, 0, listHeroClass) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, parent)
    }

    private fun initView(position: Int, parent: ViewGroup): View {
        val categoryHero = getItem(position)
        val view =
            LayoutInflater.from(context).inflate(R.layout.item_layout_class, parent, false)
        view.apply {
            categoryHero?.let {
                imageViewClass.setImageResource(it.image)
                textViewClass.text = it.nameClass
            }
        }
        return view
    }
}
