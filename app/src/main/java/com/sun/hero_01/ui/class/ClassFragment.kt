package com.sun.hero_01.ui.`class`

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.CategoryHero
import com.sun.hero_01.data.model.Hero
import com.sun.hero_01.data.model.HeroClasses
import com.sun.hero_01.data.model.HeroDifficulties
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.HeroRemoteDataSource
import com.sun.hero_01.ui.detail.DetailFragment
import com.sun.hero_01.utils.HeroClass
import com.sun.hero_01.utils.HeroDifficulty
import com.sun.hero_01.utils.OnItemRecyclerViewListener
import com.sun.hero_01.utils.extensions.replaceFragment
import kotlinx.android.synthetic.main.fragment_class.*

class ClassFragment : BaseFragment(), ClassContract.View, OnItemRecyclerViewListener<Hero> {

    override val layoutResourceId = R.layout.fragment_class

    private var className: String? = null
    private var difficulty: HeroDifficulty? = null
    private val adapter by lazy { ClassAdapter(this) }
    private val presenter =
        ClassPresenter(HeroRepository.getInstance(HeroRemoteDataSource.getInstance()))

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initData()
    }

    override fun loadFilterHeroOnSuccess(heroes: MutableList<Hero>) {
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

    private fun initView() {
        recyclerViewHero.apply {
            setHasFixedSize(true)
            adapter = this@ClassFragment.adapter
        }
        val spinnerClassAdapter = SpinnerAdapter(requireContext(), HeroClasses.getHeroClasses())
        spinnerClass.adapter = spinnerClassAdapter
        val spinnerDiffAdapter =
            SpinnerAdapter(requireContext(), HeroDifficulties.getHeroDifficulty())
        spinnerDifficulty.adapter = spinnerDiffAdapter
    }

    private fun initData() {
        spinnerClass.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val heroClass = parent?.getItemAtPosition(position) as CategoryHero
                className = when (heroClass.nameClass) {
                    HeroClass.ANY_CLASS -> null
                    else -> heroClass.nameClass
                }
                presenter.apply {
                    setView(this@ClassFragment)
                    getFilterHeroes(className, difficulty)
                }
            }
        }

        spinnerDifficulty.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val heroDifficulty = parent?.getItemAtPosition(position) as CategoryHero
                difficulty = when (heroDifficulty.nameClass) {
                    HeroDifficulty.EASY.nameDiff -> HeroDifficulty.EASY
                    HeroDifficulty.AVERAGE.nameDiff -> HeroDifficulty.AVERAGE
                    HeroDifficulty.HARD.nameDiff -> HeroDifficulty.HARD
                    HeroDifficulty.SEVERE.nameDiff -> HeroDifficulty.SEVERE
                    else -> null
                }
                presenter.apply {
                    setView(this@ClassFragment)
                    getFilterHeroes(className, difficulty)
                }
            }
        }
    }

    companion object {
        fun newInstance() = ClassFragment()
    }
}
