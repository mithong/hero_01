package com.sun.hero_01.data.source

import com.sun.hero_01.data.model.Favourite

class FavouriteRepository private constructor(
    private val local: HeroDataSource.Local
){
    fun insertHero(hero: Favourite) = local.insertHero(hero)
    fun deleteHero(idHero: String) = local.deleteHero(idHero)
    fun getAllHero() = local.getAllHero()
    fun checkFavorite(id: String) = local.checkFavorite(id)
    fun searchHero(heroName: String) = local.searchHero(heroName)

    companion object {
        private var instance: FavouriteRepository? = null

        fun getInstance(
            local: HeroDataSource.Local
        ): FavouriteRepository {
            return instance ?: FavouriteRepository(local).also {
                instance = it
            }
        }
    }
}
