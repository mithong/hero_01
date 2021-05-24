package com.sun.hero_01.ui.compare

import com.sun.hero_01.base.BasePresenter
import com.sun.hero_01.data.model.HeroDetail

interface CompareContract {
    interface View {
        fun loadFirstHeroDetailOnSuccess(firstHeroDetail: HeroDetail)
        fun loadSecondHeroDetailOnSuccess(heroDetail: HeroDetail)
        fun onError(exception: Exception?)
    }

    interface Presenter: BasePresenter<View> {
        fun getCompareHeroDetail(firstId: String, secondId: String)
    }
}
