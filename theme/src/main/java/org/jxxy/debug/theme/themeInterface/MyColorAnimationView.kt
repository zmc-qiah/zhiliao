package org.jxxy.debug.theme.themeInterface

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.animation.ValueAnimator.AnimatorUpdateListener
import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.yarolegovich.discretescrollview.DiscreteScrollView
import org.jxxy.debug.theme.adapter.ThemeDsvViewHolder

class MyColorAnimationView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    View(context, attrs, defStyleAttr), AnimatorUpdateListener, Animator.AnimatorListener {
    var colorAnim: ValueAnimator? = null
    private val mPageChangeListener: PageChangeListener
    var onPageChangeListener: DiscreteScrollView.ScrollStateChangeListener<ThemeDsvViewHolder>? = null
    fun setmViewPager(mViewPager: DiscreteScrollView, count: Int, vararg colors: Int) {
// 		this.mViewPager = mViewPager;
        checkNotNull(mViewPager.adapter) { "ViewPager does not have adapter instance." }
        mPageChangeListener.viewPagerChildCount = count
        mViewPager.addScrollStateChangeListener(object :
            DiscreteScrollView.ScrollStateChangeListener<ThemeDsvViewHolder> {
            override fun onScrollStart(
                currentItemHolder: ThemeDsvViewHolder,
                adapterPosition: Int
            ) {
                mPageChangeListener.onScrollStart(currentItemHolder, adapterPosition)
            }

            override fun onScrollEnd(
                currentItemHolder: ThemeDsvViewHolder,
                adapterPosition: Int
            ) {
                mPageChangeListener.onScrollEnd(currentItemHolder, adapterPosition)
            }
            override fun onScroll(
                scrollPosition: Float,
                currentPosition: Int,
                newPosition: Int,
                currentHolder: ThemeDsvViewHolder?,
                newCurrent: ThemeDsvViewHolder?
            ) {
                mPageChangeListener.onScroll(scrollPosition, currentPosition, newPosition, currentHolder, newCurrent)
            }
        })
        if (colors.size == 0) {
            createDefaultAnimation()
        } else {
            createAnimation(*colors)
        }
    }

    init {
        mPageChangeListener = PageChangeListener()
    }

    private fun seek(seekTime: Long) {
        if (colorAnim == null) {
            createDefaultAnimation()
        }
        colorAnim!!.currentPlayTime = seekTime
    }

    private fun createAnimation(vararg colors: Int) {
        if (colorAnim == null) {
            colorAnim = ObjectAnimator.ofInt(
                this,
                "backgroundColor",
                *colors
            )
            (colorAnim as ObjectAnimator?)?.setEvaluator(ArgbEvaluator())
            (colorAnim as ObjectAnimator?)?.setDuration(DURATION.toLong())
            (colorAnim as ObjectAnimator?)?.addUpdateListener(this)
        }
    }

    private fun createDefaultAnimation() {
        colorAnim = ObjectAnimator.ofInt(
            this,
            "backgroundColor",
            RED,
            BLUE,
            GREEN,
            WHITE
        )
        (colorAnim as ObjectAnimator?)?.setEvaluator(ArgbEvaluator())
        (colorAnim as ObjectAnimator?)?.setDuration(DURATION.toLong())
        (colorAnim as ObjectAnimator?)?.addUpdateListener(this)
    }

    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationEnd(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}
    override fun onAnimationUpdate(animation: ValueAnimator) {
        invalidate()
    }

    private inner class PageChangeListener :
        DiscreteScrollView.ScrollStateChangeListener<ThemeDsvViewHolder> {
        var viewPagerChildCount: Int = 0
        override fun onScrollStart(
            currentItemHolder: ThemeDsvViewHolder,
            adapterPosition: Int
        ) {
            onPageChangeListener?.onScrollStart(currentItemHolder, adapterPosition)
        }

        override fun onScrollEnd(
            currentItemHolder: ThemeDsvViewHolder,
            adapterPosition: Int
        ) {
            onPageChangeListener?.onScrollEnd(currentItemHolder, adapterPosition)
        }
        override fun onScroll(
            scrollPosition: Float,
            currentPosition: Int,
            newPosition: Int,
            currentHolder: ThemeDsvViewHolder?,
            newCurrent: ThemeDsvViewHolder?
        ) {
            val count = viewPagerChildCount - 1
            if (count != 0) {
                var temp = scrollPosition
                if (temp < 0) temp *= -1
                val length = (currentPosition + temp) / count
                val progress = (length * DURATION).toInt()
                seek(progress.toLong())
            }
            onPageChangeListener?.onScroll(scrollPosition, currentPosition, newPosition, currentHolder, newCurrent)
        }
    }

    companion object {
        private const val RED = -0x7f80
        private const val BLUE = -0x7f7f01
        private const val WHITE = -0x1
        private const val GREEN = -0xB5E61D
        private const val DURATION = 3000
    }
}
