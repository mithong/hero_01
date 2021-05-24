package com.sun.hero_01.ui.favorite

import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.base.BasePresenter

interface FavoriteContact {

    interface View {
        fun localFavouriteOnSuccess(hero: MutableList<Favourite>)
    }

    interface Presenter : BasePresenter<View> {
        fun getAllHero(): MutableList<Favourite>
        fun deleteHero(idHero: String): Boolean
        fun searchHero(heroName: String): MutableList<Favourite>
    }
}
