package com.sun.hero_01.ui.favorite

import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.source.FavouriteRepository
import com.sun.hero_01.data.source.HeroRepository

class FavoritePresenter(
    private val favouriteRepository: FavouriteRepository
) : FavoriteContact.Presenter  {

    private var view: FavoriteContact.View? = null

    override fun insertHero(hero: Favourite) = favouriteRepository.insertHero(hero)

    override fun getAllHero() = favouriteRepository.getAllHero()

    override fun deleteHero(idHero: String) = favouriteRepository.deleteHero(idHero)

    override fun checkFavorite(id: Int) = favouriteRepository.checkFavorite(id)

    override fun onStart() {
        view?.localFavouriteOnSuccess(getAllHero())
    }

    override fun onStop() {
        view = null
    }

    override fun setView(view: FavoriteContact.View?) {
        this.view = view
    }
}
