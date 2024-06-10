package org.jxxy.debug.search.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ItemDecoration


class Identity_Space_HorzItemDecoration(private val spacebottom: Int,private val spaceHor:Int) : ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        //格子底部的间距
        outRect.bottom = spacebottom
        outRect.left=spaceHor
        outRect.right=spaceHor


    }
}