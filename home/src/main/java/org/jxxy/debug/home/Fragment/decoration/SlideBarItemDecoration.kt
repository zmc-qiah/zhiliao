package org.jxxy.debug.home.Fragment.decoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.dp
import org.jxxy.debug.home.R

class SlideBarItemDecoration(private val context: Context) : RecyclerView.ItemDecoration() {
    // 滑动条整体宽度
    private val scrollWidth: Float = 45f.dp()

    // 滑动条选中部分宽度
    private val indicatorWidth: Int = 15f.dp()

    // 滑动条的高度
    private val barHeight: Int = 4f.dp()

    // recyclerview ITEM到滑动条的距离
    private val paddingBottom: Int = 10f.dp()

    // 滑动条圆角
    private val indicatorRadius: Float = 100f.dp()
    private val paint = Paint()

    init {
        paint.isAntiAlias = true
    }

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDraw(c, parent, state)
        // 居中
        val barX = (parent.width / 2 - scrollWidth / 2)
        val barY = (parent.height - barHeight).toFloat()
        // 画底部背景
        paint.color = ResourceUtil.getColor(R.color.灰)
        c.drawRoundRect(
            barX,
            barY,
            barX + scrollWidth,
            barY + barHeight,
            indicatorRadius,
            indicatorRadius,
            paint
        )
        // 计算滑动比例
        val extent = parent.computeHorizontalScrollExtent()
        val range = parent.computeHorizontalScrollRange()
        val offset = parent.computeHorizontalScrollOffset()
        val maxEndX = (range - extent).toFloat()
        if (maxEndX > 0) {
            val proportion = offset / maxEndX

            val scrollableDistance = scrollWidth - indicatorWidth

            val offsetX = scrollableDistance * proportion
            paint.color = ResourceUtil.getColor(R.color.primary200)
            // 画滑动选中条
            c.drawRoundRect(
                barX + offsetX,
                barY,
                barX + indicatorWidth.toFloat() + offsetX,
                barY + barHeight,
                indicatorRadius,
                indicatorRadius,
                paint
            )
        } else {
            paint.color = ResourceUtil.getColor(R.color.primary200)
            c.drawRoundRect(
                barX,
                barY,
                barX + scrollWidth,
                barY + barHeight,
                indicatorRadius,
                indicatorRadius,
                paint
            )
        }
    }

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.bottom = barHeight + paddingBottom
    }
}