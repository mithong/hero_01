package com.sun.hero_01.data.source.local.database

import com.sun.hero_01.data.model.Favourite

interface HeroFavourite {
    fun insertHero(hero: Favourite): Boolean
    fun deleteHero(idHero: String): Boolean
    fun getAllHero(): MutableList<Favourite>
    fun checkFavorite(id: Int): Boolean
}
