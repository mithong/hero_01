package com.sun.hero_01.ui.search

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.HeroRemoteDataSource
import com.sun.hero_01.ui.detail.DetailFragment
import com.sun.hero_01.utils.OnItemRecyclerViewListener
import com.sun.hero_01.utils.extensions.replaceFragment
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment() : BaseFragment(), SearchContract.View, OnItemRecyclerViewListener<Hero> {

    override val layoutResourceId = R.layout.fragment_search
    override var bottomNavigationViewVisibility = View.GONE

    private val adapter by lazy { SearchAdapter(this) }
    private val presenter =
        SearchPresenter(HeroRepository.getInstance(HeroRemoteDataSource.getInstance()))
    private var nameHero: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadSearchHero()
    }

    override fun loadSearchHeroOnSuccess(heroes: MutableList<Hero>) {
        adapter.updateData(heroes)
    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onItemClickListener(itemHero: Hero?) {
        replaceFragment(DetailFragment.newInstance(itemHero?.name), R.id.frameContainer)
    }

    override fun onStop() {
        super.onStop()
        presenter.onStop()
    }

    private fun initData() {
        arguments?.let {
            nameHero = it.getString(SearchFragment.ARGUMENT_HERO_NAME).toString()
        }

        presenter.apply {
            setView(this@SearchFragment)
            nameHero?.let { getSearchHero(it) }
        }
    }

    private fun initView() {
        recyclerViewHero.apply {
            setHasFixedSize(true)
            adapter = this@SearchFragment.adapter
        }
    }

    private fun loadSearchHero() {
        buttonSearch.setOnClickListener {
            presenter.getSearchHero(editTextHeroName.text.toString())
        }
    }

    companion object {
        private const val ARGUMENT_HERO_NAME = "ARGUMENT_HERO_NAME"

        fun newInstance(keyHeroName: String) = SearchFragment().apply {
            arguments = bundleOf(ARGUMENT_HERO_NAME to keyHeroName)
        }
    }
}
