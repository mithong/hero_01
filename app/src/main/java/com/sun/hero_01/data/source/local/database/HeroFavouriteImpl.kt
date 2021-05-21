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

    override fun checkFavorite(id: String): Boolean {
        val selection = "$HERO_ID=?"
        val cursor = readableDB.query(
            HERO_TABLE,
            arrayOf(HERO_ID),
            selection,
            arrayOf(id),
            null,
            null,
            null
        )
        val count = cursor.count
        return count > 0
    }

    override fun searchHero(heroName: String): MutableList<Favourite> {
        val hero = mutableListOf<Favourite>()
        val cursor = readableDB.rawQuery(
            "SELECT * FROM $HERO_TABLE WHERE " +
                    "$HERO_ID LIKE '"+"%"+heroName+"%"+"'",
            null
        )
        cursor?.run {
            while (moveToNext()) {
                hero.add(Favourite(this))
            }
        }
        return hero
    }

    companion object {
        private var instance: HeroFavouriteImpl? = null

        fun getInstance(data: FavouriteDatabase): HeroFavouriteImpl =
            instance ?: HeroFavouriteImpl(data).also { instance = it }
    }
}
