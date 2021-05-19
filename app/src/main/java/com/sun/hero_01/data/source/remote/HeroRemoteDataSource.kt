package com.sun.hero_01.data.source.remote

import com.sun.hero_01.data.source.HeroDataSource
import com.sun.hero_01.data.source.remote.fetchdata.GetJsonFromUrl
import com.sun.hero_01.utils.Constant
import com.sun.hero_01.utils.KeyEntityType

class HeroRemoteDataSource : HeroDataSource.Remote {

    override fun <T> getDataHeroDetail(id: String, listener: OnFetchDataJsonListener<T>) {
        val stringUrl =
            "${Constant.BASE_URL}/${Constant.BASE_VERSION}/${Constant.BASE_LANGUAGE}/${Constant.PATH_CHAMPION_DETAIL}/$id.json"

        GetJsonFromUrl(
            listener,
            KeyEntityType.HERO_DETAIL
        ).execute(stringUrl)
    }

    override fun <T> getDataListHero(listener: OnFetchDataJsonListener<T>) {
        val stringUrl =
            "${Constant.BASE_URL}/${Constant.BASE_VERSION}/${Constant.BASE_LANGUAGE}/${Constant.PATH_LIST_CHAMPION}"

        GetJsonFromUrl(
            listener,
            KeyEntityType.LIST_HERO
        ).execute(stringUrl)
    }

    companion object {
        private var instance: HeroRemoteDataSource? = null

        fun getInstance() = instance ?: HeroRemoteDataSource().also { instance = it }
    }
}
