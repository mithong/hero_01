package com.sun.hero_01.utils

import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.sun.hero_01.utils.extensions.getSnapPosition

class SnapOnScrollListener(
    private val snapHelper: SnapHelper,
    var onSnapPositionChange: (Int) -> Unit
) : RecyclerView.OnScrollListener() {

    private var snapPosition = RecyclerView.NO_POSITION

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        notifySnapPositionChange(recyclerView)
    }

    private fun notifySnapPositionChange(recyclerView: RecyclerView) {
        val snapPosition = snapHelper.getSnapPosition(recyclerView)
        val snapPositionChanged = this.snapPosition != snapPosition
        if (snapPositionChanged) {
            onSnapPositionChange(snapPosition)
            this.snapPosition = snapPosition
        }
    }
}
