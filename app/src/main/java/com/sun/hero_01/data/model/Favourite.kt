package com.sun.hero_01.data.model

import android.content.ContentValues
import android.database.Cursor
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favourite(
    val heroId: String,
    val heroName: String,
    val heroUrlImage: String
): Parcelable{
    constructor(cursor: Cursor) : this(
        heroId = cursor.getString(cursor.getColumnIndex(HERO_ID)),
        heroName = cursor.getString(cursor.getColumnIndex(HERO_NAME)),
        heroUrlImage = cursor.getString(cursor.getColumnIndex(HERO_IMAGE))
    )

    companion object {
        const val HERO_TABLE = "tb_hero"
        const val HERO_ID = "id"
        const val HERO_NAME = "name"
        const val HERO_IMAGE = "image"
    }
}
