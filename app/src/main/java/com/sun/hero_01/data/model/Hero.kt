package com.sun.hero_01.data.model
data class Hero (
    val name: String?,
    val title: String?,
    val difficulty: Int?,
    val image: String?
)

object HeroEntity {
    const val DATA = "data"
    const val NAME = "name"
    const val TITLE = "title"
    const val INFO = "info"
    const val DIFFICULTY = "difficulty"
    const val IMAGE = "image"
    const val IMAGE_FULL = "full"
}
