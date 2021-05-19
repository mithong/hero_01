package com.sun.hero_01.ui.champion

import com.sun.hero_01.base.BasePresenter
import com.sun.hero_01.data.model.Hero

interface ChampionContract {

    interface View {
        fun loadListHeroOnSuccess(heroes: MutableList<Hero>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getListHero()
    }
}
