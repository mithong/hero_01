package com.sun.hero_01.ui.favorite

import android.os.Bundle
import android.view.View
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.source.FavouriteRepository
import com.sun.hero_01.data.source.local.FavouriteLocalDataSource
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : BaseFragment(), FavoriteContact.View {

    private var favoritePresenter: FavoritePresenter? = null

    private val favoriteAdapter by lazy {
        FavoriteAdapter({}, {_,_-> })
    }

    override val layoutResourceId = R.layout.fragment_favorite

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }


    override fun localFavouriteOnSuccess(hero: MutableList<Favourite>) {
        favoriteAdapter.updateData(hero)
    }

    private fun onViewCreated() {
        onInitRecyclerView()
        favoritePresenter = FavoritePresenter(
            FavouriteRepository.getInstance(FavouriteLocalDataSource.getInstance(requireActivity()))
        )
        favoritePresenter?.let {
            it.setView(this)
            it.onStart()
        }
    }

    private fun onInitRecyclerView() {
        recyclerHeroFavorite.apply {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    companion object {
        private var instance: FavoriteFragment? = null

        fun newInstance(): FavoriteFragment = instance ?: FavoriteFragment()
            .also { instance = it }
    }
}
