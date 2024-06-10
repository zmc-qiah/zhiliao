package org.jxxy.debug.thinkMap.MyListener

import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View

class ClickListener(private val onClick: () -> Unit, private val onDoubleClick: () -> Unit,private val onLongClick:()->Unit) : View.OnTouchListener {
    private var clickCount = 0
    private val doubleClickTimeout = 500
    private val longClickTimeout = 300
    private var time = 0L
    private val handler = Handler(Looper.getMainLooper())

    private val resetClickCountRunnable = Runnable {
        if (clickCount == 1) {
            onClick()
        }
        clickCount = 0
    }

    override fun onTouch(view: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                clickCount++
                time = System.currentTimeMillis()
                handler.removeCallbacks(resetClickCountRunnable)
                handler.postDelayed(resetClickCountRunnable, doubleClickTimeout.toLong())
            }
            MotionEvent.ACTION_UP -> {
                if (clickCount >= 2) {
                    onDoubleClick()
                    clickCount = 0
                    handler.removeCallbacks(resetClickCountRunnable)
                }
                val oldTime = time
                time = System.currentTimeMillis()
                if (time - oldTime > longClickTimeout){
                    onLongClick()
                }
            }
        }
        return true
    }
}
