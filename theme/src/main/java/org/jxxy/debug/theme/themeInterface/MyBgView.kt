package org.jxxy.debug.theme.themeInterface

import android.animation.ArgbEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.widget.LinearLayout
import androidx.annotation.ArrayRes
import org.jxxy.debug.common.scheme.SchemeAI
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.theme.R

class MyBgView@JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :LinearLayout(context, attrs, defStyleAttr) {
    private var gradientPaint: Paint? = null
    private var currentGradient: IntArray? = null
    private var evaluator: ArgbEvaluator? = null
    init {
        evaluator = ArgbEvaluator();
        gradientPaint =Paint (Paint.ANTI_ALIAS_FLAG);
        setWillNotDraw(false);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        inflate(getContext(), R.layout.view_forecast, this);
    }
    fun onScroll(fraction: Float, ci: Int, ni: Int) {
        currentGradient = mix(
            fraction,
            weatherToGradient(ci)!!,
            weatherToGradient(ni)!!
        )!!
        initGradient()
        invalidate()
    }
    private fun mix(fraction: Float, c1: IntArray, c2: IntArray): IntArray? {
        evaluator.nullOrNot(
            {return null},
            {
                return intArrayOf(
                    (it.evaluate(fraction, c1[0], c2[0]) as Int),
                    (it.evaluate(fraction, c1[1], c2[1]) as Int),
                    (it.evaluate(fraction, c1[2], c2[2]) as Int)
                )
            }
        )
    return null
    }
    override fun onDraw(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), gradientPaint!!)
        super.onDraw(canvas)
    }
    private fun weatherToGradient(index:Int): IntArray? {
        Log.d("TAG", "weatherToGradient: ${index}")
        return when (index) {
            SchemeAI.EMO -> colors(R.array.gradientMostlyCloudy)
            SchemeAI.ADV-> colors(R.array.gradientClear)
            SchemeAI.POSE -> colors(R.array.gradientCloudy)
            SchemeAI.PAINT -> colors(R.array.gradientMostlyCloudy)
            SchemeAI.KNOWLEDGE -> colors(R.array.gradientMostlyCloudy)
            SchemeAI.NAVIGATION -> colors(R.array.gradientPeriodicClouds)
            SchemeAI.OCR -> colors(R.array.gradientClear)
            else -> colors(R.array.gradientPeriodicClouds)
        }
    }
    private fun initGradient() {
        val centerX = width * 0.5f
        currentGradient?.let {
            val gradient: Shader = LinearGradient(
                centerX, 0f, centerX, height.toFloat(),
                it, null,
                Shader.TileMode.MIRROR
            )
            gradientPaint?.shader = gradient
        }
    }
    private fun colors(@ArrayRes res: Int): IntArray? {
        return context.resources.getIntArray(res)
    }
    fun setIndex(index: Int){
        currentGradient = weatherToGradient(index)
        if (this.height!=0&&this.width!=0){
            initGradient()
        }
        invalidate()
    }
}