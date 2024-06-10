package cn.lentme.projects.whitetile.core

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.view.SurfaceView
import java.util.concurrent.ConcurrentLinkedQueue
import kotlin.math.roundToLong

class RenderView: SurfaceView, SurfaceHolder.Callback, Runnable {
    private val tag = "RenderView"

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context,attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    private var renderThread = Thread(this)
    private var isRunning = false
    private var frameQueue = ConcurrentLinkedQueue<Bitmap>()
    private var paint: Paint = Paint()
    private var canvas: Canvas? = null

    private var bgBitmap: Bitmap

    init {
        paint.color = Color.WHITE
        paint.isAntiAlias = true
        paint.textSize = 16f

        holder.setFixedSize(480, 800)
        holder.addCallback(this)
        val bit = this.resources.assets.open("bg.png")
        bgBitmap = BitmapFactory.decodeStream(bit)
        bit.close()
    }

    fun addFrame(bitmap: Bitmap): Boolean {
        return frameQueue.offer(bitmap)
    }

    override fun run() {
        var y = 0f
        val speed = 5
        var fps = 0f
        var last = System.nanoTime()
        var counter = 0
        var temp: Long
        var start: Long
        var end: Long
        while(true) {
            start = System.currentTimeMillis()
            canvas = holder.lockCanvas()
            canvas?.let {
                it.drawColor(Color.BLACK)
                it.drawBitmap(bgBitmap, 0f, y, paint)
                it.drawText("fps: $fps", 0f, 16f, paint)
                holder.unlockCanvasAndPost(it)
            }
            y += speed
            temp = System.nanoTime()
            counter++
            if (counter == 20) {
                fps = (100000000000.0 / (temp - last)).roundToLong() / 100.0f
                counter = 0
            }
            last = temp
            end = System.currentTimeMillis()
            if(end - start < UiSleep)
                Thread.sleep(UiSleep - (end - start))
        }
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        Log.d(tag, "surface changed")
        isRunning = true
        renderThread = Thread(this)
        renderThread.start()
    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {
        isRunning = false
        Log.d(tag, "surface destroyed")
    }

    override fun surfaceCreated(holder: SurfaceHolder) {
        Log.d(tag, "surface created")
    }
}