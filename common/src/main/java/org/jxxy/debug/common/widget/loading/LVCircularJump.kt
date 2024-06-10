package com.debug.myapplication.widget.loading

// https://github.com/ldoublem/LoadingView
// setViewColor(int color)
// startAnim(int time)
// stopAnim()
import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
class LVCircularJump : LVBase {
    private var mPaint: Paint? = null
    private var mWidth = 0f
    private var mHigh = 0f
    private val mMaxRadius = 6f
    private val circularCount = 4
    private var mAnimatedValue = 0f
    private var mJumpValue = 0

    constructor(context: Context?) : super(context!!)
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context!!,
        attrs
    )

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context!!,
        attrs,
        defStyleAttr
    )

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        mWidth = measuredWidth.toFloat()
        mHigh = measuredHeight.toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val circularX = mWidth / circularCount
        for (i in 0 until circularCount) {
            if (i == mJumpValue % circularCount) {
                canvas.drawCircle(
                    i * circularX + circularX / 2f,
                    mHigh / 2 - mHigh / 2 * mAnimatedValue,
                    mMaxRadius,
                    mPaint!!
                )
            } else {
                canvas.drawCircle(
                    i * circularX + circularX / 2f,
                    mHigh / 2,
                    mMaxRadius,
                    mPaint!!
                )
            }
        }
    }

    private fun initPaint() {
        mPaint = Paint()
        mPaint!!.isAntiAlias = true
        mPaint!!.style = Paint.Style.FILL
        mPaint!!.color = Color.WHITE
    }

    fun setViewColor(color: Int) {
        mPaint!!.color = color
        postInvalidate()
    }

    override fun InitPaint() {
        initPaint()
    }

    override fun OnAnimationUpdate(valueAnimator: ValueAnimator?) {
        mAnimatedValue = valueAnimator?.animatedValue as Float
        if (mAnimatedValue > 0.5) {
            mAnimatedValue = 1 - mAnimatedValue
        }
        invalidate()
    }

    override fun OnAnimationRepeat(animation: Animator?) {
        mJumpValue++
    }

    override fun SetAnimRepeatMode(): Int {
        return ValueAnimator.RESTART
    }

    override fun OnStopAnim(): Int {
        mAnimatedValue = 0f
        mJumpValue = 0
        return 0
    }

    override fun AinmIsRunning() {}
    override fun SetAnimRepeatCount(): Int {
        return ValueAnimator.INFINITE
    }
}
