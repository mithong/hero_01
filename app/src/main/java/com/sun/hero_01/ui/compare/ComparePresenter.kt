package com.sun.hero_01.ui.compare

import com.sun.hero_01.data.model.HeroDetail
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener

class ComparePresenter(
    private val repository: HeroRepository?
): CompareContract.Presenter {

    private var view: CompareContract.View? = null

    override fun getCompareHeroDetail(idFirstHero: String, idSecondHero: String) {
        repository?.apply {
            getHeroDetails(idFirstHero, object :
                OnFetchDataJsonListener<HeroDetail> {
                override fun onSuccess(firstData: HeroDetail) {
                    view?.loadFirstHeroDetailOnSuccess(firstData)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            })
            getHeroDetails(idSecondHero, object :
                OnFetchDataJsonListener<HeroDetail> {
                override fun onSuccess(secondData: HeroDetail) {
                    view?.loadSecondHeroDetailOnSuccess(secondData)
                }

                override fun onError(exception: Exception?) {
                    view?.onError(exception)
                }
            })
        }
    }

    override fun onStart() {
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: CompareContract.View?) {
        this.view = view
    }
}
