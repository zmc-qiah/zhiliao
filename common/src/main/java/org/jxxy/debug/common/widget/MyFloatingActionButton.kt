package org.jxxy.debug.common.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MyFloatingActionButton  @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FloatingActionButton(context, attrs, defStyleAttr) {
    private var startX = 0
    private var startY = 0

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_MOVE -> {
                x = event.rawX - startX
                y = event.rawY - startY
            }
        }
        return super.onTouchEvent(event)
    }
}