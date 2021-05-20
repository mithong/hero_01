package com.sun.hero_01.ui.compare

import android.os.Bundle
import android.view.View
import com.sun.hero_01.R
import com.sun.hero_01.base.BaseFragment

class CompareFragment {

    companion object {
        private var instance: CompareFragment? = null

        fun newInstance(): CompareFragment = instance ?: CompareFragment()
            .also { instance = it }
    }
}
