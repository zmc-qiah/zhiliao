package org.jxxy.debug.society.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class SpaceItemDecoration(private val spacebottom: Int,val spaceleft: Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //格子底部的间距
        outRect.left=spaceleft
        outRect.bottom = spacebottom


    }
}