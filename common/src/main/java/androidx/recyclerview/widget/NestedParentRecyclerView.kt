package androidx.recyclerview.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.core.view.NestedScrollingParent2
import androidx.core.view.NestedScrollingParent3
import androidx.core.view.NestedScrollingParentHelper
import androidx.core.view.ViewCompat
import org.jxxy.debug.common.R

class NestedParentRecyclerView
    @JvmOverloads
    constructor(
        context: Context,
        attributeSet: AttributeSet? = null,
        i: Int = 0,
    ) : RecyclerView(
            context,
            attributeSet,
            i,
        ),
        NestedScrollingParent3,
        NestedScrollingParent2 {
        private var target: ViewGroup? = null
        private val tempLocation = IntArray(2)
        private val tempConsumed = IntArray(2)
        private val tempConsumed2 = IntArray(2)
        private val mParentHelper: NestedScrollingParentHelper = NestedScrollingParentHelper(this)

        override fun onChildAttachedToWindow(child: View) {
            if (child.getTag(R.id.ceiling_view) == true) {
                (child as? ViewGroup)?.let {
                    target = it
                }
            }
        }

        override fun onChildDetachedFromWindow(child: View) {
            if (child === target) {
                target = null
            }
        }

        override fun onInterceptTouchEvent(e: MotionEvent): Boolean {
            val isTouchTarget =
                target != null &&
                    e.y > target!!.top && e.y < target!!.bottom
            val childRv =
                if (isTouchTarget) {
                    target.findChildScrollTarget()
                } else {
                    null
                }
            var isTouchChildRv = false
            if (childRv != null) {
                childRv.getLocationOnScreen(tempLocation)
                isTouchChildRv =
                    e.rawX >= tempLocation[0] && e.rawX < tempLocation[0] + childRv.width &&
                    e.rawY >= tempLocation[1] && e.rawX < tempLocation[1] + childRv.height
            }
            return if (isTouchChildRv) false else super.onInterceptTouchEvent(e)
        }

        override fun absorbGlows(
            velocityX: Int,
            velocityY: Int,
        ) {
            if (velocityY > 0) {
                target.findChildScrollTarget()?.fling(velocityX, velocityY) ?: super.absorbGlows(velocityX, velocityY)
            } else {
                super.absorbGlows(velocityX, velocityY)
            }
        }

        override fun onStartNestedScroll(
            child: View,
            target: View,
            axes: Int,
            type: Int,
        ): Boolean {
            return (axes and ViewCompat.SCROLL_AXIS_VERTICAL) != 0
        }

        override fun onStartNestedScroll(
            child: View,
            target: View,
            nestedScrollAxes: Int,
        ): Boolean {
            return onStartNestedScroll(child, target, nestedScrollAxes, ViewCompat.TYPE_TOUCH)
        }

        override fun onNestedScrollAccepted(
            child: View,
            target: View,
            axes: Int,
            type: Int,
        ) {
            mParentHelper.onNestedScrollAccepted(child, target, axes, type)
            startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL, type)
        }

        override fun onNestedScrollAccepted(
            child: View,
            target: View,
            axes: Int,
        ) {
            onNestedScrollAccepted(child, target, axes, ViewCompat.TYPE_TOUCH)
        }

        override fun onStopNestedScroll(
            target: View,
            type: Int,
        ) {
            mParentHelper.onStopNestedScroll(target, type)
        }

        override fun onStopNestedScroll(child: View) {
            onStopNestedScroll(child, ViewCompat.TYPE_TOUCH)
        }

        override fun onNestedScroll(
            target: View,
            dxConsumed: Int,
            dyConsumed: Int,
            dxUnconsumed: Int,
            dyUnconsumed: Int,
            type: Int,
            consumed: IntArray,
        ) {
            if (dyUnconsumed == 0) {
                return
            }
            tempConsumed[1] = 0
            doScrollConsumed(0, dyUnconsumed, tempConsumed)
            val mConsumedY = tempConsumed[1]
            val mUnConsumedY = dyUnconsumed - tempConsumed[1]
            consumed[1] += mConsumedY
            // 大于0是向上，小于0是手指向下滑，
            if (type == ViewCompat.TYPE_TOUCH && mUnConsumedY < 0) {
                scrollState = SCROLL_STATE_DRAGGING
            }
        }

        override fun onNestedScroll(
            target: View,
            dxConsumed: Int,
            dyConsumed: Int,
            dxUnconsumed: Int,
            dyUnconsumed: Int,
        ) {
            onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, ViewCompat.TYPE_TOUCH)
        }

        override fun onNestedScroll(
            target: View,
            dxConsumed: Int,
            dyConsumed: Int,
            dxUnconsumed: Int,
            dyUnconsumed: Int,
            type: Int,
        ) {
            onNestedScroll(target, dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, ViewCompat.TYPE_TOUCH, tempConsumed2)
        }

        override fun onNestedPreScroll(
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
        ) {
            onNestedPreScroll(target, dx, dy, consumed, ViewCompat.TYPE_TOUCH)
        }

        override fun onNestedPreScroll(
            target: View,
            dx: Int,
            dy: Int,
            consumed: IntArray,
            type: Int,
        ) {
            // 问问我的parent要不要处理
            val isParentScroll = dispatchNestedPreScroll(dx, dy, consumed, null, type)
            if (isParentScroll) return
            if (dy > 0) {
                doScrollConsumed(0, dy, tempConsumed)
                consumed[1] = tempConsumed[1]
                scrollState =
                    if (type == ViewCompat.TYPE_TOUCH) {
                        SCROLL_STATE_DRAGGING
                    } else {
                        SCROLL_STATE_SETTLING
                    }
            }
        }

        private fun doScrollConsumed(
            dx: Int,
            dy: Int,
            consumed: IntArray,
        ) {
            consumed[0] = 0
            consumed[1] = 1
            scrollStep(dx, dy, consumed)
            val consumedX = consumed[0]
            val consumedY = consumed[1]
            if (consumedX != 0 || consumedY != 0) {
                dispatchOnScrolled(consumedX, consumedY)
            }
        }
        override fun onNestedFling(
            target: View,
            velocityX: Float,
            velocityY: Float,
            consumed: Boolean
        ): Boolean {
            if (!consumed) {
                dispatchNestedFling(0f, velocityY, true)
                fling(0, velocityY.toInt())
                return true
            }
            return false
        }

        override fun onNestedPreFling(target: View, velocityX: Float, velocityY: Float): Boolean {
            return dispatchNestedPreFling(velocityX, velocityY)
        }
    }

fun View?.findChildScrollTarget(): RecyclerView? {
    var res: RecyclerView? = null
    when {
        this == null ||
            !this.isEnabled -> res = null
        this.getTag(R.id.ceiling_rv) == true && this is RecyclerView -> res = this
        this !is ViewGroup -> res = null
        else -> {
            for (i in 0 until this.childCount) {
                val temp = this.getChildAt(i).findChildScrollTarget()
                if (temp != null) {
                    res = temp
                    break
                }
            }
        }
    }
    return res
}
