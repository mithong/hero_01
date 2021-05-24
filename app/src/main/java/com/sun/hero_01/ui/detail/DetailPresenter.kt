package com.sun.hero_01.ui.detail

import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.model.HeroDetail
import com.sun.hero_01.data.model.HeroSpell
import com.sun.hero_01.data.source.FavouriteRepository
import com.sun.hero_01.data.source.remote.OnFetchDataJsonListener
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.utils.HeroSpellSymbol

class DetailPresenter(
    private val repository: HeroRepository,
    private val favoriteRepository: FavouriteRepository
): DetailContract.Presenter {

    private var view: DetailContract.View? = null

    override fun getHeroDetail(id: String) {
        repository.getHeroDetails(id, object :
            OnFetchDataJsonListener<HeroDetail> {
            override fun onSuccess(data: HeroDetail) {
                changeSpellNameToSymbol(data)
                if (checkFavorite(data.id.toString()))
                    data.isFavorite = true
                view?.loadHeroDetailOnSuccess(data)
            }

            override fun onError(exception: Exception?) {
                view?.onError(exception)
            }
        })
    }
    private fun changeSpellNameToSymbol(data: HeroDetail) {
        val listSpells = mutableListOf<HeroSpell>()
        data.apply {
            passive?.let {
                listSpells.add(
                    HeroSpell(
                        HeroSpellSymbol.PASSIVE,
                        it.name,
                        it.description,
                        it.image
                    )
                )
            }
            spells?.let {
                for (item in it) {
                    listSpells.add(
                        HeroSpell(
                            HeroSpellSymbol.SKILL[it.indexOf(item)],
                            item.name,
                            item.description,
                            item.image
                        )
                    )
                }
            }
        }
        data.apply { spells = listSpells }
    }

    override fun insertHero(hero: Favourite) = favoriteRepository.insertHero(hero)

    override fun deleteHero(idHero: String) = favoriteRepository.deleteHero(idHero)

    override fun checkFavorite(idHero: String) = favoriteRepository.checkFavorite(idHero)

    override fun onStart() {
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: DetailContract.View?) {
        this.view = view
    }
}
