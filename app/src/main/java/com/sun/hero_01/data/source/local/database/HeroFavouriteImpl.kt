package com.sun.hero_01.data.source.local.database

import android.content.ContentValues
import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.model.Favourite.Companion.HERO_ID
import com.sun.hero_01.data.model.Favourite.Companion.HERO_IMAGE
import com.sun.hero_01.data.model.Favourite.Companion.HERO_NAME
import com.sun.hero_01.data.model.Favourite.Companion.HERO_TABLE

class HeroFavouriteImpl private constructor(
    private val database: FavouriteDatabase
) : HeroFavourite {

    private val writableDB = database.writableDatabase
    private val readableDB = database.readableDatabase

    override fun insertHero(hero: Favourite) =
        ContentValues().apply {
            put(HERO_ID, hero.heroId)
            put(HERO_NAME, hero.heroName)
            put(HERO_IMAGE, hero.heroUrlImage)
        }.run {
            writableDB.insert(
                HERO_TABLE,
                null,
                this
            )
        } > 0


    override fun deleteHero(idHero: String)=
        writableDB.delete(
            HERO_TABLE,
            "$HERO_ID=?",
            arrayOf(idHero)
        ) > 0

    override fun getAllHero(): MutableList<Favourite> {
        val hero = mutableListOf<Favourite>()
        val cursor = readableDB.query(
            HERO_TABLE,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )
        cursor?.run {
            while (moveToNext()) {
                hero.add(Favourite(this))
            }
        }
        cursor.close()
        return hero
    }

    override fun checkFavorite(id: Int): Boolean {
        val selection = "$HERO_ID=?"
        val cursor = readableDB.query(
            HERO_TABLE,
            arrayOf(HERO_ID),
            selection,
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        val count = cursor.count
        cursor.close()
        database.close()
        return count > 0
    }

    companion object {
        private var instance: HeroFavouriteImpl? = null

        fun getInstance(data: FavouriteDatabase): HeroFavouriteImpl =
            instance ?: HeroFavouriteImpl(data).also { instance = it }
    }
}
