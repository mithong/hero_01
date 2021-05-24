package com.sun.hero_01.data.model

import com.sun.hero_01.R
import com.sun.hero_01.utils.HeroClass
import com.sun.hero_01.utils.HeroDifficulty

data class CategoryHero (
    val image: Int,
    val nameClass: String
)

object HeroClasses {

    private val imageClasses = intArrayOf(
        R.drawable.ic_all_class,
        R.drawable.ic_assassin,
        R.drawable.ic_fighter,
        R.drawable.ic_mage,
        R.drawable.ic_tank,
        R.drawable.ic_support,
        R.drawable.ic_marksman
    )

    private val heroClasses = arrayListOf(
        HeroClass.ANY_CLASS,
        HeroClass.ASSASSIN,
        HeroClass.FIGHTER,
        HeroClass.MAGE,
        HeroClass.TANK,
        HeroClass.SUPPORT,
        HeroClass.MARKSMAN
    )

    fun getHeroClasses() : MutableList<CategoryHero> {
        val listClasses = mutableListOf<CategoryHero>()
        for (i in imageClasses.indices) {
            val imageID = imageClasses[i]
            val heroClassName = heroClasses[i]
            listClasses.add(CategoryHero(imageID, heroClassName))
        }
        return listClasses
    }
}

object HeroDifficulties {

    private val imageDifficulties = intArrayOf(
        R.drawable.ic_all_class,
        R.drawable.ic_easy,
        R.drawable.ic_average,
        R.drawable.ic_hard,
        R.drawable.ic_severe
    )

    private val heroDifficulties = arrayListOf(
        HeroDifficulty.ANY.nameDiff,
        HeroDifficulty.EASY.nameDiff,
        HeroDifficulty.AVERAGE.nameDiff,
        HeroDifficulty.HARD.nameDiff,
        HeroDifficulty.SEVERE.nameDiff
    )

    fun getHeroDifficulty() : MutableList<CategoryHero> {
        val listDifficulties = mutableListOf<CategoryHero>()
        for (i in imageDifficulties.indices) {
            val imageID = imageDifficulties[i]
            val heroClassName = heroDifficulties[i]
            listDifficulties.add(CategoryHero(imageID, heroClassName))
        }
        return listDifficulties
    }
}
