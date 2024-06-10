package org.jxxy.debug.home.Fragment.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.home.R

class TimelineItemDecoration(context: Context) : RecyclerView.ItemDecoration() {
    private val circleRadius = 8f // 时间节点圆圈的半径
    private val circleStrokeWidth = 4f // 时间节点圆圈的描边宽度
    private val lineStrokeWidth = 2f // 时间轴线的宽度
    private val lineColor = Color.GRAY // 时间轴线的颜色
    private val dateMarginStart = 16 // 日期文本距离左边的间距

    private val paint: Paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = lineColor
        paint.strokeWidth = lineStrokeWidth
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val itemCount = parent.adapter?.itemCount ?: 0

        for (i in 0 until itemCount) {
            val child: View = parent.getChildAt(i) ?: continue
            val itemPosition = parent.getChildAdapterPosition(child)

            if (itemPosition != RecyclerView.NO_POSITION) {
                val isLastItem = itemPosition == itemCount - 1

                val startX = child.left.toFloat() + child.translationX + dateMarginStart.toFloat()
                val startY = child.top.toFloat() + child.translationY

                val endX = startX
                val endY = if (isLastItem) {
                    startY + child.height / 2f
                } else {
                    child.bottom.toFloat() + child.translationY
                }

                val circleX = startX
                val circleY = startY

                // 绘制时间轴线
                c.drawLine(startX, startY, endX, endY, paint)

                if (!isLastItem) {
                    // 绘制时间节点圆圈
                    val circlePaint = Paint()
                    circlePaint.isAntiAlias = true
                    circlePaint.color = ContextCompat.getColor(parent.context, R.color.primary200)
                    c.drawCircle(circleX, circleY, circleRadius, circlePaint)
                }
            }
        }
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val itemPosition = parent.getChildAdapterPosition(view)
        val itemCount = parent.adapter?.itemCount ?: 0

        if (itemPosition != RecyclerView.NO_POSITION) {
            if (itemPosition != itemCount - 1) {
                outRect.set(0, 0, 0, 0)
            } else {
                outRect.set(0, 0, 0, 0)
            }
        }
    }
}