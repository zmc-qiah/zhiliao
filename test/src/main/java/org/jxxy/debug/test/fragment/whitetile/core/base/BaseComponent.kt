package cn.lentme.projects.whitetile.core.base

import android.graphics.Canvas
import android.view.MotionEvent

abstract class BaseComponent {

    data class Position(var x: Float, var y: Float)

    protected var position: Position = Position(0f, 0f)

    fun setPosition(x: Float, y: Float) {
        this.position.x = x
        this.position.y = y
    }

    fun translate(x: Float, y: Float) {
        this.position.x += x
        this.position.y += y
    }

    abstract fun onTouchEvent(event: MotionEvent?, scX: Float, scY: Float): Boolean
    abstract fun update(deltaTime: Float): Boolean
    abstract fun draw(canvas: Canvas)
}