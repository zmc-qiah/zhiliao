package cn.lentme.projects.whitetile.game.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import cn.lentme.projects.whitetile.core.*
import cn.lentme.projects.whitetile.core.base.BaseComponent

class TestComponent(var x: Float, var y: Float): BaseComponent() {
    private val tag = "TestComponent"

    private var speed = 1.4f
    private var width = GameFrameWidth / 4
    private var height = GameFrameHeight / 4
    private var paint = Paint()

    companion object {
        private var black = 0
    }

    init {
        if( x.toInt() == (black++) % 4 )
            paint.color = Color.BLACK
        else
            paint.color = Color.WHITE

        x *= width
        y *= height
    }

    override fun onTouchEvent(event: MotionEvent?, scX: Float, scY: Float): Boolean {
        return false
    }

    override fun update(deltaTime: Float): Boolean {
        y += speed * deltaTime
        return y < GameFrameHeight - height
    }

    override fun draw(canvas: Canvas) {
        canvas.drawRect(x + 1, y + 1, x + width - 1, y + height - 1, paint)
    }
}