package com.sun.hero_01.utils.extensions

import androidx.appcompat.app.ActionBar
import com.sun.hero_01.R
import com.sun.hero_01.utils.ToolbarIcon

fun ActionBar?.showIcon(icon: ToolbarIcon){
    this?.apply {
        when (icon) {
            ToolbarIcon.APP -> {
                setDisplayShowHomeEnabled(true)
                setDisplayHomeAsUpEnabled(false)
                setLogo(R.mipmap.ic_app)
            }
            ToolbarIcon.RETURN -> {
                setDisplayShowHomeEnabled(false)
                setDisplayHomeAsUpEnabled(true)
            }
        }
    }
}
