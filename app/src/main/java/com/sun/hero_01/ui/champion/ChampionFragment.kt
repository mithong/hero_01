package com.sun.hero_01.ui.champion

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.HeroRemoteDataSource
import com.sun.hero_01.ui.compare.CompareFragment
import com.sun.hero_01.ui.detail.DetailFragment
import com.sun.hero_01.ui.search.SearchFragment
import com.sun.hero_01.utils.OnItemRecyclerViewListener
import com.sun.hero_01.utils.extensions.replaceFragment
import kotlinx.android.synthetic.main.fragment_champion.*
import kotlinx.android.synthetic.main.item_layout_hero.*

class ChampionFragment : BaseFragment(), ChampionContract.View, OnItemRecyclerViewListener<Hero> {

    private val adapterChampion by lazy {
        ChampionAdapter(this) { idName, view ->
            onShowMenu(idName, view)
        }
    }

    private fun onShowMenu(its: String?, view: View?) {
        PopupMenu(context,view).apply {
            menuInflater.inflate(R.menu.menu_popup, this.menu)
            setOnMenuItemClickListener {
                when(it.itemId){
                    R.id.menuCompare -> {
                        onAddHero(its)
                        true
                    }
                    else -> false
                }
            }
            show()
        }
    }

    private fun onAddHero(its: String?) {
        val sharedPreferences: SharedPreferences? =
            context?.getSharedPreferences(PREF_COMPARE_HERO, Context.MODE_PRIVATE)
        val firstHero = resources.getString(R.string.first_hero)
        val secondHero = resources.getString(R.string.second_hero)
        sharedPreferences?.edit()?.apply {
            if (sharedPreferences?.getString(firstHero, "").equals("")) {
                putString(firstHero, its)
            } else if (sharedPreferences?.getString(firstHero, "").equals(its)) {
                Toast.makeText(context, resources.getString(R.string.cantcompare), Toast.LENGTH_LONG)
                    .show()
            } else {
                putString(secondHero, its)
                replaceFragment(CompareFragment.newInstance(), R.id.frameContainer)
            }
            apply()
        }
    }

    private val presenter =
        ChampionPresenter(HeroRepository.getInstance(HeroRemoteDataSource.getInstance()))
    override val layoutResourceId = R.layout.fragment_champion

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initView()
    }

    override fun onStart() {
        super.onStart()
        loadSearchFragment()
    }

    override fun loadListHeroOnSuccess(heroes: MutableList<Hero>) {
        adapterChampion.updateData(heroes)
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
        replaceFragment(DetailFragment.newInstance(itemHero?.id), R.id.frameContainer)
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
            adapter = this@ChampionFragment.adapterChampion
        }
    }

    private fun loadSearchFragment() {
        buttonSearch.setOnClickListener {
            replaceFragment(
                SearchFragment.newInstance(editTextHeroName.text.toString()),
                R.id.frameContainer
            )
        }
    }

    companion object {
        private const val PREF_COMPARE_HERO = "PREF_COMPARE_HERO"

        fun newInstance() = ChampionFragment()
    }
}
