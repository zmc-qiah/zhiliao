package org.jxxy.debug.home.Fragment.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class GridSpacingItemDecoration(private val spacing: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildAdapterPosition(view)
        val column = position % 2

        outRect.left = spacing - column * spacing / 2
        outRect.right = (column + 1) * spacing / 2
        outRect.bottom = spacing
    }
}