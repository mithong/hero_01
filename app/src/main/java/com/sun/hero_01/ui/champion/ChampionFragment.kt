package com.sun.hero_01.ui.champion

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.HeroRemoteDataSource
import com.sun.hero_01.ui.detail.DetailFragment
import com.sun.hero_01.utils.OnItemRecyclerViewListener
import com.sun.hero_01.utils.extensions.replaceFragment
import kotlinx.android.synthetic.main.fragment_champion.*

class ChampionFragment : BaseFragment(), ChampionContract.View, OnItemRecyclerViewListener<Hero> {

    private val adapter by lazy { ChampionAdapter(this) }
    private val presenter =
        ChampionPresenter(HeroRepository.getInstance(HeroRemoteDataSource.getInstance()))
    override val layoutResourceId = R.layout.fragment_champion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    override fun loadListHeroOnSuccess(heroes: MutableList<Hero>) {
        adapter.updateData(heroes)
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    override fun onItemClickListener(itemHero: Hero?) {
        replaceFragment(DetailFragment.newInstance(itemHero?.name), R.id.frameContainer)
    }

    private fun initData() {
        presenter.apply {
            setView(this@ChampionFragment)
            onStart()
        }
    }

    private fun initView() {
        recyclerViewHero.apply {
            setHasFixedSize(true)
            adapter = this@ChampionFragment.adapter
        }
    }

    companion object {
        fun newInstance() = ChampionFragment()
    }
}
