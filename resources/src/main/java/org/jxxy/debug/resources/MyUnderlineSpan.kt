package org.jxxy.debug.resources

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.text.style.ReplacementSpan
import org.jxxy.debug.corekit.util.ResourceUtil
import kotlin.math.roundToInt

class MyUnderlineSpan(private val drawable: Drawable) : ReplacementSpan() {
    private val padding: Rect = Rect()
    var textColor = ResourceUtil.getColor(org.jxxy.debug.common.R.color.black)

    init {
        drawable.getPadding(padding)
    }
    override fun draw(canvas: Canvas, text: CharSequence, start: Int, end: Int, x: Float, top: Int, y: Int, bottom: Int, paint: Paint) {
        val rect = RectF(x, top.toFloat(), x + measureText(paint, text, start, end), bottom.toFloat())
        drawable.setBounds(rect.left.toInt() - padding.left,
            rect.top.toInt() - padding.top,
            rect.right.toInt() + padding.right,
            (rect.bottom.toInt() + rect.height() / 1.2f).toInt())
        paint.color = textColor
        canvas.drawText(text, start, end, x, y.toFloat(), paint)

        drawable.draw(canvas)
    }
    override fun getSize(paint: Paint, text: CharSequence, start: Int, end: Int, fm: Paint.FontMetricsInt?): Int = paint.measureText(text, start, end).roundToInt()

    private fun measureText(paint: Paint, text: CharSequence, start: Int, end: Int): Float = paint.measureText(text, start, end)
}