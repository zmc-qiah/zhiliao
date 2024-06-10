package cn.lentme.projects.whitetile.game.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.view.MotionEvent
import android.view.SurfaceHolder
import cn.lentme.projects.whitetile.core.*
import cn.lentme.projects.whitetile.core.base.BaseGameView
import cn.lentme.projects.whitetile.game.component.TileComponent

class TileScreenView(context: Context): BaseGameView(context), SurfaceHolder.Callback {
    private val tag = "TestScreenView"

    private var paint = Paint()
    private var tileComponent = TileComponent()

    private var _touchX = 0f
    private var _touchY = 0f
    private var scX: Float = 1f
    private var scY: Float = 1f

    init {
        paint.color = Color.RED
        paint.isAntiAlias = true
        paint.textSize = BaseTextSize
        holder.addCallback(this)
    }

    override fun drawScene(canvas: Canvas?) {
        canvas?.let { it ->
            it.drawColor(Color.BLACK)

            tileComponent.draw(canvas)

            it.drawText("fps: $mFPS", 0f, BaseTextSize, paint)
//            it.drawText("ft: $mFT", 0f, BaseTextSize * 2, paint)
//            it.drawText("${_touchX * scX} ${_touchY * scX} ", 0f, BaseTextSize * 3, paint)
        }
    }

    override fun update(deltaTime: Float) {
        tileComponent.update(deltaTime)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(tag, "width: ${this.width}  height: ${this.height}")
        scX = GameFrameWidth.toFloat() / this.width
        scY = GameFrameHeight.toFloat() / this.height
        Log.d(tag, "scx: $scX  scY: $scY")
        viewCreated()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        release()
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        _touchX = event?.getX(0)!! * scX
        _touchY = event.getY(0) * scY
        tileComponent.onTouchEvent(event, scX, scY)

        if(event.action == MotionEvent.ACTION_UP)
            performClick()

        return true
    }

}