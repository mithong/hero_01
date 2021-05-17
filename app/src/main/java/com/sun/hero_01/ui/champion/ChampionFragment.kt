package com.sun.hero_01.ui.champion

import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment

class ChampionFragment : BaseFragment() {

    override val layoutResourceId = R.layout.fragment_champion

    companion object {
        fun newInstance() = ChampionFragment()
    }
}
