package com.sun.hero_01.ui.`class`

import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener
import com.sun.hero_01.utils.HeroDifficulty

class ClassPresenter(private val repository: HeroRepository) : ClassContract.Presenter {

    private var view: ClassContract.View? = null

    override fun getFilterHeroes(classFilter: String?, difficultyFilter: HeroDifficulty?) {
        repository.getListHero(object : OnFetchDataJsonListener<MutableList<Hero>> {
            override fun onSuccess(data: MutableList<Hero>) {
                view?.loadFilterHeroOnSuccess(getDataFilter(data, classFilter, difficultyFilter))
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun onStart() {}

    override fun onStop() {
        view = null
    }

    override fun setView(view: ClassContract.View?) {
        this.view = view
    }

    private fun getDataFilter(
        data: MutableList<Hero>,
        classFilter: String?,
        difficultyFilter: HeroDifficulty?
    ): MutableList<Hero> {
        val heroes = mutableListOf<Hero>()
        if (classFilter != null){
            data.forEach {
                if (classFilter in arrayOf(it.primaryTag, it.secondaryTag)) {
                    heroes += it
                }
            }
        } else {
            heroes.addAll(data)
        }
        if (difficultyFilter != null){
            return heroes.filter {
                it.difficulty in difficultyFilter.diff
            }.toMutableList()
        }
        return heroes
    }
}
