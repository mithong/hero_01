package com.sun.hero_01.ui.search

import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener

class SearchPresenter(private val repository: HeroRepository?) : SearchContract.Presenter {

    private var view: SearchContract.View? = null

    override fun getListHeroSearch(heroName: String) {
        repository?.getListHero(object : OnFetchDataJsonListener<MutableList<Hero>> {
            override fun onSuccess(data: MutableList<Hero>) {
                view?.loadSearchHeroOnSuccess(getDataFilter(data, heroName))
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun onStart() {}

    fun getSearchHero(heroName: String) {
        getListHeroSearch(heroName)
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: SearchContract.View?) {
        this.view = view
    }

    private fun getDataFilter(data: MutableList<Hero>, nameHero: String): MutableList<Hero> {
        val heroes = mutableListOf<Hero>()
        for (hero in data) {
            hero.name?.let {
                if (hero.name.startsWith(nameHero)) {
                    heroes.add(hero)
                }
            }
        }
        return heroes
    }
}
