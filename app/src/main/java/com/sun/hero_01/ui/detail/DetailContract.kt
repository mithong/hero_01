package com.sun.hero_01.ui.detail

import com.sun.hero_01.base.BasePresenter
import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.model.HeroDetail

interface DetailContract {

    interface View {
        fun loadHeroDetailOnSuccess(heroDetail: HeroDetail)
        fun onError(exception: Exception?)
    }

    interface Presenter: BasePresenter<View> {
        fun getHeroDetail(id: String)
        fun insertHero(hero: Favourite): Boolean
        fun deleteHero(idHero: String): Boolean
        fun checkFavorite(idHero: String): Boolean
    }
}
