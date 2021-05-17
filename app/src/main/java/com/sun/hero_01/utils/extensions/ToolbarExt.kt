package com.sun.hero_01.utils.extensions

import androidx.appcompat.app.ActionBar
import com.sun.hero_01.R

fun ActionBar?.showIcon(){
    this?.apply {
        setLogo(R.mipmap.ic_app)
    }
}
