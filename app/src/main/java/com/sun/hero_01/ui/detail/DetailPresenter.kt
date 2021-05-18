package com.sun.hero_01.ui.detail

import com.sun.hero_01.data.model.HeroDetail
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener
import com.sun.hero_01.data.source.HeroRepository

class DetailPresenter(private val repository: HeroRepository?): DetailContract.Presenter {

    private var view: DetailContract.View? = null

    override fun getHeroDetail(id: String) {
        repository?.getHeroDetails(id, object:
            OnFetchDataJsonListener<HeroDetail> {
            override fun onSuccess(data: HeroDetail) {
                view?.loadHeroDetailOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }

    override fun onStart() {
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: DetailContract.View?) {
        this.view = view
    }
}
