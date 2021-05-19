package com.sun.hero_01.data.source.local

import android.content.Context
import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.source.HeroDataSource
import com.sun.hero_01.data.source.local.database.FavouriteDatabase
import com.sun.hero_01.data.source.local.database.HeroFavouriteImpl

class FavouriteLocalDataSource private constructor(
    private val heroFavouriteImplement: HeroFavouriteImpl
) : HeroDataSource.Local{

    override fun insertHero(hero: Favourite) = heroFavouriteImplement.insertHero(hero)

    override fun deleteHero(idHero: String) = heroFavouriteImplement.deleteHero(idHero)

    override fun getAllHero() = heroFavouriteImplement.getAllHero()

    override fun checkFavorite(idHero: Int) = heroFavouriteImplement.checkFavorite(idHero)

    override fun searchHero(heroName: String) = heroFavouriteImplement.searchHero(heroName)

    companion object {
        @Volatile
        private var instance: FavouriteLocalDataSource? = null

        fun getInstance(context: Context): HeroDataSource.Local =
            instance ?: FavouriteLocalDataSource(
                HeroFavouriteImpl.getInstance(
                    FavouriteDatabase.getDatabase(context)
                )
            ).also { instance = it }
    }
}
