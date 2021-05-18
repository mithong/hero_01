package com.sun.hero_01.data.source

import com.sun.hero_01.data.model.HeroDetail
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener

class HeroRepository private constructor(private val remote: HeroDataSource.Remote){

    fun getHeroDetails(id: String, listener: OnFetchDataJsonListener<HeroDetail>) {
        remote.getDataHeroDetail(id, listener)
    }

    companion object {
        private var instance: HeroRepository? = null

        fun getInstance(
            remote: HeroDataSource.Remote
        ): HeroRepository {
            return instance ?: synchronized(this) {
                instance ?: HeroRepository(remote).also { instance = it }
            }
        }
    }
}
