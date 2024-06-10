package org.jxxy.debug.thinkMap.treeview

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import org.jxxy.debug.thinkMap.MyListener.MyTreeViewListener
import kotlin.math.sqrt

class MyTreeView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : GysoTreeView(context, attrs, defStyleAttr) {
    val clickThreshold = 5 // 可以根据需要调整点击的判断阈值
    val clickTimeout = 100 // 可以根据需要调整点击的判断时间阈值
    var startX = 0f
    var startY = 0f
    var listener: MyTreeViewListener? = null
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = event.x
                startY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                val endX = event.x
                val endY = event.y
                val distance = calculateDistance(startX, startY, endX, endY)
                val timeElapsed = event.eventTime - event.downTime
                if (distance < clickThreshold && timeElapsed < clickTimeout) {
                    listener?.onClick()
                }
            }
        }
        return super.onTouchEvent(event)
    }
    fun calculateDistance(x1: Float, y1: Float, x2: Float, y2: Float): Float {
        val deltaX = x2 - x1
        val deltaY = y2 - y1
        return sqrt(deltaX * deltaX + deltaY * deltaY)
    }
}
