package com.sun.hero_01

import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.model.HeroDetail

object FakeData {
    const val  ID_HERO = "Name"

    val HERO_DETAIL = HeroDetail(
        id = "Hero",
        name = "Hero Name",
        title = "Title",
        skins = mutableListOf(),
        passive = null,
        primaryTag = "Primary Tag",
        secondaryTag = "Secondary Tag",
        image = "Image",
        info = null,
        lore = "Lore",
        spells = mutableListOf(),
        stats = null,
        isFavorite = false
    )

    val HEROES = mutableListOf(
        Hero(
            id = "Aatrox",
            name = "Aatrox",
            title = "the Darkin Blade",
            difficulty = 4,
            primaryTag = "Primary Tag",
            secondaryTag = "Secondary Tag",
            image = "Aatrox.png"
        ),
        Hero(
            id = "Chogath",
            name = "Cho'Gath",
            title = "the Terror of the Void",
            difficulty = 4,
            primaryTag = "Primary Tag",
            secondaryTag = "Secondary Tag",
            image = "Chogath.png"
        ),
        Hero(
            id = "Corki",
            name = "Corki",
            title = "the Daring Bombardier",
            difficulty = 4,
            primaryTag = "Primary Tag",
            secondaryTag = "Secondary Tag",
            image = "Corki.png"
        )
    )
}
