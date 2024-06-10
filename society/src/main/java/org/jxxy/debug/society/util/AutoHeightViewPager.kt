package org.jxxy.debug.society.util


import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.viewpager.widget.ViewPager

class AutoHeightViewPager(context: Context, attrs: AttributeSet) : ViewPager(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val view = getChildAt(currentItem)
        view?.let {
            it.measure(widthMeasureSpec, heightMeasureSpec)
        }
        setMeasuredDimension(measuredWidth, measureHeight(heightMeasureSpec, view))
    }

    private fun measureHeight(measureSpec: Int, view: View?): Int {
        var result = 0
        val specMode = View.MeasureSpec.getMode(measureSpec)
        val specSize = View.MeasureSpec.getSize(measureSpec)

        if (specMode == View.MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            if (view != null) {
                result = view.measuredHeight
            }
            if (specMode == View.MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    fun measureView(view: View) {
        val intw = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val inth = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        view.measure(intw, inth)
        val intwidth = view.measuredWidth
        val intheight = view.measuredHeight
    }

}
