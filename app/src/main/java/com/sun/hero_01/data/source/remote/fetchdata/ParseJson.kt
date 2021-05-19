package com.sun.hero_01.data.source.remote.fetchdata

import com.sun.hero_01.data.model.*
import com.sun.hero_01.utils.KeyEntityType
import org.json.JSONObject

class ParseJson {

    fun onParseJsonToHeroDetail(jsonObject: JSONObject) = jsonObject.run {
        var heroName = ""
        val jsonInfo =
            getJSONObject(HeroDetailEntry.DETAIL_OBJECT).also { heroName = it.keys().next() }
        val jsonDetail = jsonInfo.getJSONObject(heroName)

        with(jsonDetail) {
            val listSkin = ParseDataWithJson().parseJsonArray(
                getJSONArray(HeroDetailEntry.SKIN_OBJECT),
                KeyEntityType.LIST_SKIN
            ) as List<HeroSkin>
            val listInfo = ParseDataWithJson().parseJsonObject(
                getJSONObject(HeroDetailEntry.INFO_OBJECT),
                KeyEntityType.LIST_INFO
            ) as HeroInfo
            val listStat = ParseDataWithJson().parseJsonObject(
                getJSONObject(HeroDetailEntry.STATS_OBJECT),
                KeyEntityType.LIST_STAT
            ) as HeroStat
            val listSpell = ParseDataWithJson().parseJsonArray(
                getJSONArray(HeroDetailEntry.SPELLS_OBJECT),
                KeyEntityType.LIST_SPELL
            ) as List<HeroSpell>
            val listPassive = ParseDataWithJson().parseJsonObject(
                getJSONObject(HeroDetailEntry.PASSIVE_OBJECT),
                KeyEntityType.LIST_PASSIVE
            ) as HeroSpell

            HeroDetail(
                id = heroName,
                title = getString(HeroDetailEntry.TITLE),
                image = getJSONObject(HeroDetailEntry.IMAGE_OBJECT).getString(HeroDetailEntry.IMAGE),
                skins = listSkin,
                lore = getString(HeroDetailEntry.LORE),
                primaryTag = getJSONArray(HeroDetailEntry.TAGS_OBJECT).get(0).toString(),
                secondaryTag = getJSONArray(HeroDetailEntry.TAGS_OBJECT).opt(1).toString(),
                info = listInfo,
                stats = listStat,
                spells = listSpell,
                passive = listPassive
            )
        }
    }

    fun onParseJsonToHeroSkin(jsonObject: JSONObject) = jsonObject.run {
        HeroSkin(
            id = getString(HeroDetailEntry.ID),
            num = getInt(HeroDetailEntry.SKIN_NUM),
            name = getString(HeroDetailEntry.NAME)
        )
    }

    fun onParseJsonToHeroInfo(jsonObject: JSONObject) = jsonObject.run {
        HeroInfo(
            attack = getInt(HeroDetailEntry.INFO_ATTACK),
            defense = getInt(HeroDetailEntry.INFO_DEFENSE),
            magic = getInt(HeroDetailEntry.INFO_MAGIC),
            difficulty = getInt(HeroDetailEntry.INFO_DIFFICULTY)
        )
    }

    fun onParseJsonToHeroStat(jsonObject: JSONObject) = jsonObject.run {
        HeroStat(
            getInt(HeroDetailEntry.STATS_HP),
            getInt(HeroDetailEntry.STATS_MOVESPEED),
            getInt(HeroDetailEntry.STATS_ARMOR),
            getInt(HeroDetailEntry.STATS_ATTACK_RANGE),
            getInt(HeroDetailEntry.STATS_HP_REGEN),
            getInt(HeroDetailEntry.STATS_ATTACK_DAME),
            getDouble(HeroDetailEntry.STATS_ATTACK_SPEED).toFloat(),
            getDouble(HeroDetailEntry.STATS_SPEELBLOCK).toFloat()
        )
    }

    fun onParseJsonToHeroSpell(jsonObject: JSONObject) = jsonObject.run {
        HeroSpell(
            getString(HeroDetailEntry.ID),
            getString(HeroDetailEntry.NAME),
            getString(HeroDetailEntry.DESCRIPTION),
            getJSONObject(HeroDetailEntry.IMAGE_OBJECT).getString(HeroDetailEntry.IMAGE)
        )
    }

    fun onParseJsonToHeroPassive(jsonObject: JSONObject) = jsonObject.run {
        HeroSpell(
            null,
            getString(HeroDetailEntry.NAME),
            getString(HeroDetailEntry.DESCRIPTION),
            getJSONObject(HeroDetailEntry.IMAGE_OBJECT).getString(HeroDetailEntry.IMAGE)
        )
    }

    fun onParseJsonToListHero(jsonObject: JSONObject) = jsonObject.run {
        val jsonObjectInfo = getJSONObject(HeroEntity.INFO)
        val difficulty = jsonObjectInfo.getInt(HeroEntity.DIFFICULTY)
        val jsonObjectImage = jsonObject.getJSONObject(HeroEntity.IMAGE)
        val imageFull = jsonObjectImage.getString(HeroEntity.IMAGE_FULL)
        Hero(
            getString(HeroEntity.NAME),
            getString(HeroEntity.TITLE),
            difficulty,
            imageFull
        )
    }
}
