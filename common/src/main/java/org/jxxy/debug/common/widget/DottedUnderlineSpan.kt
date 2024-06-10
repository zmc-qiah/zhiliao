package org.jxxy.debug.common.widget

import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Paint
import android.text.TextPaint
import android.text.style.CharacterStyle
import org.jxxy.debug.corekit.util.ResourceUtil

class DottedUnderlineSpan: CharacterStyle() {
    override fun updateDrawState(tp: TextPaint) {
        tp.color = ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200)
        tp.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
    }

    fun getSize(
        paint: Paint,
        text: CharSequence,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        return paint.measureText(text, start, end).toInt()
    }

    fun draw(
        canvas: Canvas,
        text: CharSequence,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        val oldColor = paint.color
        val oldStyle = paint.style
        paint.color = ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200)
        paint.style = Paint.Style.STROKE
        paint.pathEffect = DashPathEffect(floatArrayOf(10f, 10f), 0f)
        canvas.drawText(text, start, end, x, y.toFloat(), paint)
        paint.color = oldColor
        paint.style = oldStyle
    }
}