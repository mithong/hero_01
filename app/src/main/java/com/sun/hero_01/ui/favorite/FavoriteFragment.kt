package com.sun.hero_01.ui.favorite

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AlertDialog
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.Favourite
import com.sun.hero_01.data.source.FavouriteRepository
import com.sun.hero_01.data.source.local.FavouriteLocalDataSource
import com.sun.hero_01.ui.detail.DetailFragment
import com.sun.hero_01.utils.extensions.replaceFragment
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : BaseFragment(), FavoriteContact.View {

    private var favoritePresenter: FavoritePresenter? = null

    private val favoriteAdapter by lazy {
        FavoriteAdapter({
            replaceFragment(DetailFragment.newInstance(it), R.id.frameContainer)
        }, { id, position ->
            onShowDialogRemove(id, position)
        })
    }

    private fun onShowDialogRemove(id: String, position: Int) {
        activity?.let {
            AlertDialog.Builder(it).apply {
                setMessage(resources.getString(R.string.remove_favorite))
                    .setPositiveButton(resources.getString(R.string.yes)) { _, _ ->
                        favoritePresenter?.deleteHero(id)
                        favoriteAdapter.removeItem(position)
                    }
                    .setNegativeButton(resources.getString(R.string.no)) { _, _ ->
                    }
                    .create()
                    .show()
            }
        }
    }

    override val layoutResourceId = R.layout.fragment_favorite

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
        onEvent()
    }

    private fun onEvent() {
        editSearchFavorite.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!editSearchFavorite.text.toString().equals("")) {
                    favoritePresenter?.let {
                        it.search(editSearchFavorite.text.toString())
                    }
                } else {
                    favoritePresenter?.let { localFavouriteOnSuccess(it.getAllHero()) }
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
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
        requireActivity().supportFragmentManager.addOnBackStackChangedListener {
            if (isCheckFavourite) {
                favoritePresenter?.onStart()
                isCheckFavourite = false
            }
        }
    }

    private fun onInitRecyclerView() {
        recyclerHeroFavorite.apply {
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }
    }

    companion object {
        var isCheckFavourite = false
        private var instance: FavoriteFragment? = null

        fun newInstance(): FavoriteFragment = instance ?: FavoriteFragment()
            .also { instance = it }
    }
}
