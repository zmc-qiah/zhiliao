package org.jxxy.debug.push.card

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.core.view.updateMarginsRelative
import androidx.viewbinding.ViewBinding
import com.igexin.sdk.PushManager
import org.jxxy.debug.common.util.UiUtil
import org.jxxy.debug.corekit.util.dp
import org.jxxy.debug.push.gson.PushCardBaseBean
import kotlin.math.abs
import kotlin.math.max

abstract class PushCardBaseView<Bean : PushCardBaseBean, ViewBind : ViewBinding> @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleArr: Int = 0) : FrameLayout(context, attrs, defStyleArr) {
    companion object {
        private const val CLICK_OFF = 5f
        private const val PUSH_CLICK = 60002
    }

    protected abstract fun bindView(inflater: LayoutInflater): ViewBind
    protected val view: ViewBind by lazy { bindView(LayoutInflater.from(context)) }

    private val statusBarHeight: Int = UiUtil.getStatusBarHeight(context)
    protected var entity: Bean? = null
    var listener: CardViewCallback? = null

    open fun bindData(entity: Bean) {
        updateMargin()
        this.entity = entity
    }

    // down时触摸点
    private val downPoint = arrayOf(0f, 0f)

    // 上一次y坐标
    private var preY = 0f

    // 初始Y值
    private var initialY = 0f

    // 滑动上界
    private var upperBound = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val holder: View = view.root
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                if (event.y > 300f) {
                    return false
                }
                downPoint[0] = event.x
                downPoint[1] = event.y
                preY = event.y
                initialY = holder.y
                upperBound = -(holder.height / 5f)
            }
            MotionEvent.ACTION_MOVE -> {
                val curY = event.y
                val diffY = preY - curY
                if (diffY > 0) {
                    // 上滑
                    holder.y -= diffY
                } else if (diffY < 0 && holder.y < initialY) {
                    // 下滑且还有下滑空间
                    holder.y -= max(diffY, holder.y - initialY)
                }
                preY = curY
            }
            MotionEvent.ACTION_UP -> {
                if (abs(downPoint[1] - event.y) < CLICK_OFF && abs(downPoint[0] - event.x) < CLICK_OFF) {
                    onClick()
                    return true
                }
                if (holder.y < upperBound) {
                    // 达到上界要求，执行消失
                    listener?.dismiss()
                } else {
                    // 执行回弹
                    holder.y = initialY
                }
            }
            MotionEvent.ACTION_CANCEL -> {
            }
        }
        return true
    }

    private fun updateMargin() {
        val lp: MarginLayoutParams? = view.root.layoutParams as? MarginLayoutParams
        lp?.let {
            it.updateMarginsRelative(12f.dp(), statusBarHeight, 12f.dp(), 12f.dp())
            view.root.layoutParams = it
        }
    }

    /**
     * 跳转并执行动画
     */
    protected open fun onClick() {
        PushManager.getInstance().sendFeedbackMessage(context, entity?.taskId, entity?.messageId, PUSH_CLICK)
        listener?.outAnim()
    }

    fun doAnim(anim: Animation?) {
        view.root.startAnimation(anim)
    }
}
