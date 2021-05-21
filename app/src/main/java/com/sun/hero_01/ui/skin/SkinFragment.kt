package com.sun.hero_01.ui.skin

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.LinearSnapHelper
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment
import com.sun.hero_01.data.model.HeroSkin
import com.sun.hero_01.utils.extensions.attachSnapHelperWithListener
import kotlinx.android.synthetic.main.fragment_skin.*

class SkinFragment : BaseFragment() {

    override val layoutResourceId = R.layout.fragment_skin
    override var bottomNavigationViewVisibility = View.GONE

    private var idHero: String? = null
    private var imageList = arrayListOf<HeroSkin>()
    private val heroSKinAdapter by lazy {
        HeroSkinAdapter(idHero)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initEvent()
    }

    override fun onStart() {
        super.onStart()
        heroSKinAdapter.updateData(imageList as MutableList<HeroSkin>?)
    }

    private fun initView() {
        arguments?.let {
            idHero = it.getString(ARGUMENT_HERO_ID).toString()
            imageList =
                it.getParcelableArrayList<HeroSkin>(ARGUMENT_HERO_SKINS) as ArrayList<HeroSkin>
        }
        recyclerHeroSkin.apply {
            setHasFixedSize(true)
            adapter = heroSKinAdapter
            attachSnapHelperWithListener(LinearSnapHelper()) {
                textSkinName.text = imageList[it].name
            }
        }
    }

    private fun initEvent() {
    }

    companion object {
        private const val ARGUMENT_HERO_ID = "ARGUMENT_HERO_ID"
        private const val ARGUMENT_HERO_SKINS = "ARGUMENT_HERO_SKINS"

        fun newInstance(heroName: String?, skins: List<HeroSkin>?) = SkinFragment().apply {
            arguments = bundleOf(ARGUMENT_HERO_ID to heroName, ARGUMENT_HERO_SKINS to skins)
        }
    }
}
