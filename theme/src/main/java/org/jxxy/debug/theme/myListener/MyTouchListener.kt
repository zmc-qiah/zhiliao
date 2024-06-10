package org.jxxy.debug.theme.myListener

import android.view.MotionEvent
import android.view.View
import android.view.WindowManager

class MyTouchListener(
    val root: View, val windowManager: WindowManager, val layoutParams: WindowManager.LayoutParams,
    val onClick:()->Unit,
    val onLongClick:()->Unit,
): View.OnTouchListener {
    private var x = 0
    private var y = 0
    private var oledx = 0
    private var oledy = 0
    private val LONG_PRESS_TIME = 1000
    private var lastTouchDownTime: Long = 0
    override fun onTouch(view: View, motionEvent: MotionEvent): Boolean {
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
            MotionEvent.ACTION_UP -> {
                val nowX = motionEvent.rawX.toInt()
                val nowY = motionEvent.rawY.toInt()
                val movedX = Math.abs(nowX - oledx)
                val movedY = Math.abs(nowY - oledy)
                if (movedX <= 20 && movedY <= 20) {
                    val nowTime = System.currentTimeMillis()
                    if (nowTime - lastTouchDownTime < LONG_PRESS_TIME){
                        onClick()
                    }else{
                        onLongClick()
                    }
                }
            }
            else -> {
            }
        }
        return true
    }
}