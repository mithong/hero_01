package com.sun.hero_01.ui.search

import com.sun.hero_01.base.BasePresenter
import com.sun.hero_01.data.model.Hero

interface SearchContract {

    interface View {
        fun loadSearchHeroOnSuccess(heroes: MutableList<Hero>)
        fun onError(exception: Exception?)
    }

    interface Presenter : BasePresenter<View> {
        fun getListHeroSearch(heroName: String)
    }
}
