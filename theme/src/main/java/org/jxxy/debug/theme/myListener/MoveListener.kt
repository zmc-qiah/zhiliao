package org.jxxy.debug.theme.myListener

import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

class MoveListener(val root: View, val windowManager: WindowManager, val layoutParams: WindowManager.LayoutParams):
    View.OnTouchListener{
    private var x = 0
    private var y = 0
    private var oledx = 0
    private var oledy = 0
    private val LONG_PRESS_TIME = 1000
    private var lastTouchDownTime: Long = 0
    override fun onTouch(view2: View, motionEvent: MotionEvent): Boolean {
        when (motionEvent.action) {
            MotionEvent.ACTION_DOWN -> {
                lastTouchDownTime = System.currentTimeMillis()
                x = motionEvent.rawX.toInt()
                oledx = motionEvent.rawX.toInt()
                y = motionEvent.rawY.toInt()
                oledy = motionEvent.rawY.toInt()
            }
            MotionEvent.ACTION_MOVE -> {
                val nowX = motionEvent.rawX.toInt()
                val nowY = motionEvent.rawY.toInt()
                val movedX = nowX - x
                val movedY = nowY - y
                x = nowX
                y = nowY
                layoutParams.apply {
                    x += movedX
                    y += movedY
                }
                windowManager.updateViewLayout(root, layoutParams)
            }
            else -> {
            }
        }
        return true
    }

}
