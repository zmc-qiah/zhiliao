package cn.lentme.projects.whitetile.core.base

import android.content.Context
import android.graphics.Canvas
import android.view.SurfaceView
import cn.lentme.projects.whitetile.core.*
import kotlin.math.roundToInt

abstract class BaseGameView(context: Context) : SurfaceView(context), Runnable  {

    // 画布变量
    private var _flag = false
    private var _canvas: Canvas? = null

    // 帧率控制变量
    private var _start = 0L
    private var _end = 0L
    private var _nanoStart = 0L
    private var _nanoEnd = 0L
    private var _fps = 0f
    private var _ft = 0L

    // 绘制线程
    private var _th: Thread? = null

    // 子类可访问的参数
    protected val mFPS get() = _fps
    protected val mFT get() = _ft

    abstract fun drawScene(canvas: Canvas?)
    abstract fun update(deltaTime: Float)

    fun release() {
        _flag = false
    }

    fun viewCreated() {
        _flag = true
        _th = Thread(this)
        _th?.start()
    }

    // SurfaceView双缓冲机制
    override fun run() {
        var counter = 0
        while(_flag) {
            try {
                _canvas = holder.lockCanvas()
                drawScene(_canvas)
                update(deltaTimeUnit)
                holder.unlockCanvasAndPost(_canvas)
            } catch (e: Exception) {

            }

            // 计算帧率
            if(++counter == 20) {
                _nanoEnd = System.nanoTime()
                _fps = (1e11f / (_nanoEnd - _nanoStart) * 20).roundToInt() / 100f
                _nanoStart = _nanoEnd
                counter = 0
            }

            _end = System.currentTimeMillis()
            _ft = _end - _start
            if (_ft < UiSleep && GameFPS < 60)
                Thread.sleep(UiSleep - (_ft))
            _start = System.currentTimeMillis()
        }
    }

    init {
        holder.setFixedSize(GameFrameWidth, GameFrameHeight)
    }

}