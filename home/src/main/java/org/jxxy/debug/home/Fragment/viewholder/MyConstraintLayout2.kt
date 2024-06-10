package org.jxxy.debug.home.Fragment.viewholder

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.constraintlayout.widget.ConstraintLayout

class MyConstraintLayout2@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        return false
    }
}