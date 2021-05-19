package com.sun.hero_01.ui.champion

import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener

class ChampionPresenter(private val repository: HeroRepository?) : ChampionContract.Presenter {

    private var view: ChampionContract.View? = null

    override fun getListHero() {
        repository?.getListHero(object : OnFetchDataJsonListener<MutableList<Hero>> {
            override fun onSuccess(data: MutableList<Hero>) {
                view?.loadListHeroOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun onStart() {
        getListHero()
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: ChampionContract.View?) {
        this.view = view
    }
}
