package com.sun.hero_01.ui.compare

import android.content.Context
import android.content.SharedPreferences
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.HeroDetail
import com.sun.hero_01.data.source.HeroRepository
import com.sun.hero_01.data.source.remote.HeroRemoteDataSource
import com.sun.hero_01.utils.ImageType
import com.sun.hero_01.utils.ToolbarIcon
import com.sun.hero_01.utils.extensions.loadHeroImage
import com.sun.hero_01.utils.extensions.showIcon
import kotlinx.android.synthetic.main.fragment_compare.*
import kotlin.math.roundToInt

class CompareFragment : BaseFragment(), CompareContract.View {

    override val layoutResourceId = R.layout.fragment_compare
    override var bottomNavigationViewVisibility = View.GONE

    private var comparePresenter: ComparePresenter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {
        val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(PREF_COMPARE_HERO, Context.MODE_PRIVATE)
        val firstHero = sharedPreferences?.getString(resources.getString(R.string.first_hero),"")
        val secondHero = sharedPreferences?.getString(resources.getString(R.string.second_hero),"")

        comparePresenter = ComparePresenter(
            HeroRepository.getInstance(HeroRemoteDataSource.getInstance())
        )

        comparePresenter?.let {
            it.setView(this)
            it.getCompareHeroDetail(firstHero.toString(), secondHero.toString())
        }
    }

    override fun loadFirstHeroDetailOnSuccess(firstHeroDetail: HeroDetail) {
        showFirstHeroData(firstHeroDetail)
    }

    override fun loadSecondHeroDetailOnSuccess(secondheroDetail: HeroDetail) {
        showSecondHeroData(secondheroDetail)
    }

    private fun showFirstHeroData(heroDetail: HeroDetail) {
        heroDetail.apply {
            textViewFirstHero.text = this.id
            imageViewFirstHero.loadHeroImage(image, ImageType.SQUARE)
            stats?.let {
                textViewHealFirstHero.text = it.hp.toString()
                textViewArmorFirstHero.text = it.armor.toString()
                textViewAttackFirstHero.text = it.attackDamage.toString()
                textViewMovementFirstHero.text = it.moveSpeed.toString()
                textViewRangeFirstHero.text = it.attackRange.toString()
                textViewSpeedFirstHero.text = it.attackSpeed.toString()
                textViewRegenFirstHero.text = it.hpRegen.toString()
                textViewMagicFirstHero.text = it.spellBlock.toString()
            }
        }
    }

    private fun showSecondHeroData(heroDetail: HeroDetail) {
        heroDetail.apply {
            textViewSecondHero.text = this.id
            imageViewSecondHero.loadHeroImage(image, ImageType.SQUARE)
            stats?.let {
                textViewHealSecondHero.text = it.hp.toString()
                textViewArmorSecondHero.text = it.armor.toString()
                textViewAttackSecondHero.text = it.attackDamage.toString()
                textViewMovementSecondHero.text = it.moveSpeed.toString()
                textViewRangeSecondHero.text = it.attackRange.toString()
                textViewSpeedSecondHero.text = it.attackSpeed.toString()
                textViewRegenSecondHero.text = it.hpRegen.toString()
                textViewMagicSecondHero.text = it.spellBlock.toString()
            }
            onShowProgressBar(progressBarHeal, textViewHealFirstHero.text.toString(), textViewHealSecondHero.text.toString())
            onShowProgressBar(progressBarRegen, textViewRegenFirstHero.text.toString(), textViewRegenSecondHero.text.toString())
            onShowProgressBar(progressBarRange, textViewRangeFirstHero.text.toString(), textViewRangeSecondHero.text.toString())
            onShowProgressBar(progressMovement, textViewMovementFirstHero.text.toString(), textViewMovementSecondHero.text.toString())
            onShowProgressBar(progressBarAttack, textViewAttackFirstHero.text.toString(), textViewAttackSecondHero.text.toString())
            onShowProgressBar(progressBarSpeed, textViewSpeedFirstHero.text.toString(), textViewSpeedSecondHero.text.toString())
            onShowProgressBar(progressBarArmor, textViewArmorFirstHero.text.toString(), textViewArmorSecondHero.text.toString())
            onShowProgressBar(progressBarMagic, textViewMagicFirstHero.text.toString(), textViewMagicSecondHero.text.toString())
        }
    }

    private fun onShowProgressBar(progressBar: ProgressBar?, first: String, second: String) {
        val firstNumber = first.toFloat()
        val secondNumber = second.toFloat()
        progressBar?.let {
            if (firstNumber > secondNumber) {
                it.progressBackgroundTintList = ColorStateList.valueOf(Color.DKGRAY)
            } else if (firstNumber < secondNumber) {
                it.progressTintList = ColorStateList.valueOf(Color.DKGRAY)
            }
            it.max = 100
            it.progress = (firstNumber * 100 / (firstNumber + secondNumber)).roundToInt()
        }

    }

    override fun onError(exception: Exception?) {
        exception?.let {
            Toast.makeText(requireActivity(), it.message, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onStop() {
        super.onStop()
        val sharedPreferences: SharedPreferences? = context?.getSharedPreferences(PREF_COMPARE_HERO, Context.MODE_PRIVATE)
        sharedPreferences?.edit()?.let {
            it.remove(resources.getString(R.string.first_hero))
            it.remove(resources.getString(R.string.first_hero))
            it.apply()
        }
    }

    override fun initToolbar() {
        this@CompareFragment.toolbar?.apply {
            title = ""
            showIcon(ToolbarIcon.RETURN)
        }
    }

    companion object {
        private const val PREF_COMPARE_HERO = "PREF_COMPARE_HERO"
        private var instance: CompareFragment? = null

        fun newInstance(): CompareFragment = instance ?: CompareFragment()
            .also { instance = it }
    }
}
