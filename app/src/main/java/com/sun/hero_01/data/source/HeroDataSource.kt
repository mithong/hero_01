package com.sun.hero_01.data.source

import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener

interface HeroDataSource {

    interface Local {
        fun insertHero(hero: Favourite): Boolean
        fun deleteHero(idHero: String): Boolean
        fun getAllHero(): MutableList<Favourite>
        fun checkFavorite(idHero: Int): Boolean
        fun searchHero(heroName: String): MutableList<Favourite>
    }

    interface Remote {
        fun <T> getDataHeroDetail(
            id: String,
            listener: OnFetchDataJsonListener<T>
        )
    }
}
