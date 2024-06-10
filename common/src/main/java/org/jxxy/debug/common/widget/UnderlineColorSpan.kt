package org.jxxy.debug.common.widget

import android.graphics.Canvas
import android.graphics.Paint
import android.text.style.LineBackgroundSpan


class UnderlineColorSpan(private val color: Int) : LineBackgroundSpan {
    override fun drawBackground(
        canvas: Canvas, paint: Paint, left: Int, right: Int, top: Int, baseline: Int, bottom: Int,
        text: CharSequence, start: Int, end: Int, lnum: Int
    ) {
        val originalStyle = paint.style
        val originalColor = paint.color

        // 设置下划线的颜色
        paint.color = color
        paint.style = Paint.Style.STROKE // 设置为画线模式，不填充
        canvas.drawRect(
            left.toFloat(),
            (bottom - 2).toFloat(),
            right.toFloat(),
            bottom.toFloat(),
            paint
        ) // 调整下划线的位置和高度

        // 恢复原始颜色和样式
        paint.style = originalStyle
        paint.color = originalColor
    }
}