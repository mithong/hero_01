package com.sun.hero_01.ui.detail

import android.os.Bundle
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.HeroDetail
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.HeroRemoteDataSource

class DetailFragment : BaseFragment(), DetailContract.View {

    override val layoutResourceId = R.layout.fragment_detail

    private var idHero: String? = null
    private var detailPresenter: DetailPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            idHero = it.getString(ARGUMENT_HERO_ID).toString()
        }

        detailPresenter =
            DetailPresenter(HeroRepository.getInstance(HeroRemoteDataSource.getInstance()))
        detailPresenter?.let {
            it.setView(this)
            idHero?.let { id -> it.getHeroDetail(id) }
        }
    }

    override fun loadHeroDetailOnSuccess(heroDetail: HeroDetail) {
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    companion object {
        private const val ARGUMENT_HERO_ID = "ARGUMENT_HERO_ID"

        fun newInstance(heroName: String?) = DetailFragment().apply {
            arguments = bundleOf(ARGUMENT_HERO_ID to heroName)
        }
    }
}
