package com.sun.hero_01.ui.search

import androidx.core.os.bundleOf
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment

class SearchFragment() : BaseFragment() {

    override val layoutResourceId = R.layout.fragment_search

    companion object {
        private const val ARGUMENT_HERO_NAME = "ARGUMENT_HERO_NAME"

        fun newInstance(keyHeroName: String) = SearchFragment().apply {
            arguments = bundleOf(ARGUMENT_HERO_NAME to keyHeroName)
        }
    }
}
