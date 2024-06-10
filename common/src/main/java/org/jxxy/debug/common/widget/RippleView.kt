package org.jxxy.debug.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Choreographer
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.Nullable
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.dp


class RippleView @JvmOverloads constructor(
    context: Context,
    @Nullable attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var color: Int =ResourceUtil.getColor(R.color.primary_100)
    private var MAX_CIRCLE_NUM = 5
    fun setColor(@ColorInt color: Int) {
        this.color = color
        invalidate()
    }
    fun setMaxCircleNum(maxCircleNum: Int) {
        MAX_CIRCLE_NUM = maxCircleNum
        invalidate()
    }
    private val DRAW_DURATION_MS = 3 * 1000 // 画一个圈4s
    private val mFrameCallback: Choreographer.FrameCallback
    private val startWidthList = mutableListOf<Int>()
    private val mStartAlpha = 150f
    private val mStartWidth = 0
    private var mWidthSpeed = 0
    private var cx = 0f
    private var cy = 0f
    private val ringPaint: Paint
    private val edgePaint: Paint
    init {
        if (attrs !=null){
            val typedArray = context.obtainStyledAttributes(attrs, R.styleable.RippleView)
            color = typedArray.getColor(R.styleable.RippleView_ripColor,ResourceUtil.getColor(R.color.primary_100) )
            MAX_CIRCLE_NUM = typedArray.getInt(R.styleable.RippleView_max,5 )
        }
        // Paint.ANTI_ALIAS_FLAG 是一个标志位，用于设置抗锯齿（Anti-Aliasing）特性。抗锯齿是一种图形渲染技术，用于减少图像边缘处的锯齿状的锯齿效果，从而让图像看起来更平滑和清晰。
        ringPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        // ringPaint.style = Paint.Style.FILL 是设置画笔（Paint）的绘制样式。在 Android 中，画笔的样式决定了绘制图形的方式。Paint.Style 是一个枚举，有三个值可选：
        // Paint.Style.FILL: 绘制实心图形，填充其内部区域。
        // Paint.Style.STROKE: 绘制空心图形，只绘制图形的轮廓线，不填充内部。
        // Paint.Style.FILL_AND_STROKE: 同时绘制实心和空心图形，既填充内部，又绘制轮廓线
        ringPaint.style = Paint.Style.FILL
        ringPaint.color = color
        edgePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        edgePaint.style = Paint.Style.STROKE
        edgePaint.strokeWidth = 2.dp()
        edgePaint.color = ResourceUtil.getColor(R.color.primary_100)
        mFrameCallback = Choreographer.FrameCallback {
            drawNext()
        }
        startWidthList.add(mStartWidth)
    }
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        // 获取圆心位置
        cx = width / 2.toFloat()
        cy = height / 2.toFloat()
        mWidthSpeed = (getMaxRadius() * 16 / DRAW_DURATION_MS).toInt()
    }
    fun start() {
        drawNext()
    }
    fun stop() {
        Choreographer.getInstance().removeFrameCallback(mFrameCallback)
    }
    private fun drawNext() {
        invalidate()
        Choreographer.getInstance().postFrameCallback(mFrameCallback)
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        stop()
    }
    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        start()
    }
    private fun getMaxRadius(): Int {
        return if (width > height) {
            height / 2
        } else {
            width / 2
        }
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        setBackgroundColor(Color.TRANSPARENT)
        for (i in startWidthList.indices) {
            val startWidth = startWidthList[i]
            // 根据半径来计算alpha值
            val alpha = mStartAlpha - startWidth * mStartAlpha / getMaxRadius()
            ringPaint.alpha = alpha.toInt()
            edgePaint.alpha = alpha.toInt()
            // 画圆
            canvas.drawCircle(cx, cy, startWidth.toFloat(), ringPaint)
            // 画圆环
            canvas.drawCircle(cx, cy, startWidth.toFloat(), edgePaint)
            // 同心圆扩散
            if (alpha > 0 && startWidth < getMaxRadius()) {
                startWidthList[i] = startWidth + mWidthSpeed
            }
        }
        val addRadius = getMaxRadius() / MAX_CIRCLE_NUM
        if (startWidthList[startWidthList.size - 1] > addRadius) {
            startWidthList.add(mStartWidth)
        }
        if (startWidthList.size > MAX_CIRCLE_NUM) {
            startWidthList.removeAt(0)
        }
    }
}