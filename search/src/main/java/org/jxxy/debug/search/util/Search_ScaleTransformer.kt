package org.jxxy.debug.search.util

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.cardview.widget.CardView
import androidx.viewpager.widget.ViewPager

class Search_ScaleTransformer(private val context: Context) : ViewPager.PageTransformer {
    private val elevation: Float

    init {
        elevation = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            20f,
            context.resources.displayMetrics
        )
    }

    override fun transformPage(page: View, position: Float) {
        if (position < -1 || position > 1) {
            // 不进行任何操作
        } else {
            val cardView = page as CardView
            if (position < 0) {
                cardView.cardElevation = (1 + position) * elevation
            } else {
                cardView.cardElevation = (1 - position) * elevation
            }
        }
    }
}
