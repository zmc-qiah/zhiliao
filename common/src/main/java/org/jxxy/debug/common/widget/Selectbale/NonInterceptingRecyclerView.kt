package org.jxxy.debug.common.widget.Selectbale

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.recyclerview.widget.RecyclerView

class NonInterceptingRecyclerView(context: Context, attributeSet: AttributeSet?) : RecyclerView(context, attributeSet) {
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        // 始终返回 false，以阻止外部 RecyclerView 拦截触摸事件
        return false
    }
}