package org.jxxy.debug.push.card

import android.app.Activity
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.WindowManager
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*
import org.jxxy.debug.push.gson.PushCardBaseBean
import java.lang.ref.WeakReference

/**
 * 个推长链透传消息卡片管理器，用于全局消息卡片推送的管理
 */
class PushCardManager private constructor() : CardViewCallback, DefaultLifecycleObserver {
    companion object {
        val instance: PushCardManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            PushCardManager()
        }
        private const val CARD_SHOW_DURATION = 8000L
        private const val ANIM_DURATION = 500L
        private const val TAG = "GeTuiPushCardManager"
    }

    private val scope by lazy {
        CoroutineScope(
            SupervisorJob() + Dispatchers.IO + CoroutineExceptionHandler { _, throwable ->
                Logger.e(TAG, throwable)
            }
        )
    }

    /**
     * 弹窗白名单，如支付流程等场景，不予展示
     */
    private val whitePageConfig = mutableMapOf<String, Boolean>()
    private val cardFactory: PushCardFactory by lazy { PushCardFactory() }

    /**
     * 当前页面名称
     */
    private var currentPageName: String? = null
    private var activityReference: WeakReference<Activity>? = null
    private var windowManager: WindowManager? = null
    private var cardView: PushCardBaseView<*, *>? = null

    private var inAnimation: AnimationSet? = null
    private var outAnimation: AnimationSet? = null
    private val outAnimListener = object : Animation.AnimationListener {
        override fun onAnimationStart(animation: Animation?) {
        }

        override fun onAnimationEnd(animation: Animation?) {
            dismiss()
        }

        override fun onAnimationRepeat(animation: Animation?) {
        }
    }
    private var timeJob: Job? = null

    var isShowing: Boolean = false
        private set
    var listener: CardManagerListener? = null

    fun updateWhiteList(pageList: Map<String, Boolean>) {
        whitePageConfig.clear()
        whitePageConfig.putAll(pageList)
    }

    fun showCard(entity: PushCardBaseBean): Boolean {
        if (isShowing) {
            // 如果有卡片正在展示，则暂不展示
            return false
        }
        val isInWhite = whitePageConfig[currentPageName.orEmpty()]
        if (isInWhite != null) {
            // 如果该页面在白名单中，则暂不展示
            return false
        }
        // 如果引用存在，则展示，否则暂不展示
        return (activityReference?.get())?.let {
            isShowing = true
            initAnim()
            windowManager = it.windowManager
            try {
                cardView = cardFactory.makeCard(entity, it, this)
                    ?: throw IllegalStateException("$entity can not make card")
                windowManager?.addView(cardView, generateLayoutParams())
                (it as? LifecycleOwner)?.lifecycle?.addObserver(this)
                cardView?.doAnim(inAnimation)
                timeJob = scope.launch {
                    delay(CARD_SHOW_DURATION)
                    withContext(Dispatchers.Main) {
                        outAnim()
                    }
                }
            } catch (e: Exception) {
                isShowing = false
                return false
            }
            true
        } ?: false
    }

    fun onActivityResume(activityName: String, reference: WeakReference<Activity>) {
        currentPageName = activityName
        activityReference?.clear()
        activityReference = reference
        listener?.onPageChange()
    }

    fun onActivityPause(activityName: String) {
        if (currentPageName == activityName) {
            activityReference?.clear()
        }
    }

    private fun initAnim() {
        if (inAnimation == null) {
            inAnimation = AnimationSet(true).apply {
                addAnimation(AlphaAnimation(0f, 1f))
                addAnimation(TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f, Animation.RELATIVE_TO_SELF, 0f))
                duration = ANIM_DURATION
            }
        }
        if (outAnimation == null) {
            outAnimation = AnimationSet(true).apply {
                addAnimation(AlphaAnimation(1f, 0f))
                addAnimation(TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -1f))
                setAnimationListener(outAnimListener)
                duration = ANIM_DURATION
            }
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        removeView()
    }

    private fun removeView() {
        try {
            if (cardView?.isAttachedToWindow == true) {
                windowManager?.removeView(cardView)
                cardView = null
            }
        } catch (e: IllegalArgumentException) {
            Logger.e(TAG, "removeView IllegalArgumentException", e)
        }
    }

    override fun dismiss() {
        removeView()
        isShowing = false
        timeJob?.cancel()
        timeJob = null
        listener?.dismiss()
    }

    override fun outAnim() {
        if ((cardView?.context as? LifecycleOwner)?.lifecycle?.currentState?.isAtLeast(Lifecycle.State.RESUMED) != true) {
            // 明确没有onResume时，直接关闭
            dismiss()
            return
        }
        // 否则执行动画再关闭
        cardView?.doAnim(outAnimation)
    }

    private fun generateLayoutParams(): WindowManager.LayoutParams {
        // 需要新建，否则会被关联window token
        return WindowManager.LayoutParams().apply {
            flags =
                // FLAG_NOT_FOCUSABLE避免window拦截所有触摸事件
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    // FLAG_TRANSLUCENT_STATUS状态栏沉浸
                    .or(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
                    // 允许window扩展至屏幕之外
                    .or(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
            // 透明背景
            format = PixelFormat.TRANSPARENT
            // PopupWindow为TYPE_APPLICATION_PANEL，TYPE_APPLICATION_SUB_PANEL的Z轴更高
            // window Y:0为状态栏位置
            type = WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL
            gravity = Gravity.TOP
            y = 100
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
        }
    }
}
