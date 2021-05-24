package com.sun.hero_01.ui.`class`

import com.sun.hero_01.base.BasePresenter
import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.utils.HeroDifficulty

interface ClassContract {

    interface View {
        fun loadFilterHeroOnSuccess(heroes: MutableList<Hero>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getFilterHeroes(classFilter: String? = null, difficultyFilter: HeroDifficulty? = null)
    }
}
