package com.sun.hero_01.data.source.local.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.sun.hero_01.data.model.Favourite

class FavouriteDatabase private constructor(
    context: Context,
    dbName: String,
    version: Int
) : SQLiteOpenHelper(context, dbName, null, version) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_HERO_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) = Unit

    companion object {
        private const val DB_NAME = "hero.db"
        private const val DB_VERSION = 1

        private const val CREATE_HERO_TABLE =
            "CREATE TABLE ${Favourite.HERO_TABLE} (" +
                    "${Favourite.HERO_ID} TEXT PRIMARY KEY, " +
                    "${Favourite.HERO_NAME} TEXT , " +
                    "${Favourite.HERO_IMAGE} TEXT) "

        private var instance: FavouriteDatabase? = null

        fun getDatabase(context: Context): FavouriteDatabase = instance ?: FavouriteDatabase(
            context,
            DB_NAME,
            DB_VERSION
        ).also { instance = it }
    }
}
