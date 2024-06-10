package cn.lentme.projects.whitetile.core

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.util.Log
import cn.lentme.projects.whitetile.core.base.BaseComponent
import java.lang.Exception
import java.util.*

class GameSystem(private val renderView: RenderView): Thread() {
    private val tag = "GameSystem"
    private val componentLinkedList = LinkedList<BaseComponent>()

    private var lastTime = 0L

    private fun update(deltaTime: Float) {
        val it = componentLinkedList.iterator()
        while(it.hasNext()) {
            if(!it.next().update(deltaTime))
                it.remove()
        }
    }

    private fun draw() {
        Log.d(tag, "lastTime: ${ System.currentTimeMillis() - lastTime}")
        val bitmap = Bitmap.createBitmap(GameFrameWidth, GameFrameHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawColor(Color.BLACK)
        componentLinkedList.forEach {
            it.draw(canvas)
        }
        try {
            val frame = Bitmap.createScaledBitmap(bitmap, renderView.width, renderView.height, false)
            renderView.addFrame(frame)
            lastTime = System.currentTimeMillis()
        }
        catch (e: Exception) {

        }
    }

    override fun run() {
        var lastTime = System.currentTimeMillis()
        var deltaTime = 0L
        while(true) {
            val errorTime = System.currentTimeMillis() - lastTime
            if(deltaTime > 1000 / GameFPS || errorTime >= 1000 / GameFPS - deltaTime) {
                deltaTime = System.currentTimeMillis()
                update(GameFPS.toFloat() / 1000)
                draw()
                lastTime = System.currentTimeMillis()
                deltaTime = System.currentTimeMillis() - deltaTime
//                Log.d(tag, "err: $errorTime")
            }
        }
    }

    fun addComponent(component: BaseComponent) {
        this.componentLinkedList.add(component)
    }

}