package cn.yonghui.hyd.category.business.ui.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.TranslateAnimation
import android.widget.TextView
import androidx.lifecycle.LifecycleCoroutineScope
import kotlinx.coroutines.*
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.widget.CommonViewSwitcher
import org.jxxy.debug.home.Fragment.item.bean.SearchHintBean
import org.jxxy.debug.home.R

class SearchHintViewSwitcher @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null
) :
    CommonViewSwitcher<SearchHintBean, TextView>(context, attrs) {
    private val emptyBean = SearchHintBean(
        0,  ResourceUtil.getString(R.string.search_value_hint)

    ).apply {
        displayKeyword = ResourceUtil.getString(R.string.search_value_hint)
    }
    private var timeJob: Job? = null
    private var searchTextSize: Float = 0f
    private var searchTextBold: Boolean = false

    companion object {
        const val DURATION: Long = 500L

        // 要算上out anim的时长，所以展示时长是4000L
        const val SCHEDULE_PERIOD: Long = 4500L
    }

    init {
        setFactory(this)
        createAnimation()
        this.supportLooper = true
    }

    fun initFactory(textSize: Float, textBold: Boolean) {
        removeAllViews()
        searchTextSize = textSize
        searchTextBold = textBold
        setFactory(this)

        inAnimation?.setAnimationListener(null)
        outAnimation?.setAnimationListener(null)
        inAnimation?.cancel()
        outAnimation?.cancel()

        createAnimation()
        scope?.get()?.let {
            val new = mutableListOf<SearchHintBean>()
            new.addAll(data)
            setData(new, it)
        }
    }

    override fun createAnimation() {
        var alphaAnimation: AlphaAnimation?
        var translateAnimation: TranslateAnimation?
        var h = height
        if (h <= 0) {
            measure(0, 0)
            h = measuredHeight
        }
        val inAnimationSet = AnimationSet(true)
        val outAnimationSet = AnimationSet(true)

        alphaAnimation = AlphaAnimation(0f, 1f)
        translateAnimation = TranslateAnimation(
            Animation.ABSOLUTE,
            0f,
            Animation.ABSOLUTE,
            0f,
            Animation.ABSOLUTE,
            h.toFloat(),
            Animation.ABSOLUTE,
            0f
        )

        inAnimationSet.addAnimation(alphaAnimation)
        inAnimationSet.addAnimation(translateAnimation)
        inAnimationSet.duration = DURATION

        alphaAnimation = AlphaAnimation(1f, 0f)
        translateAnimation = TranslateAnimation(
            Animation.ABSOLUTE,
            0f,
            Animation.ABSOLUTE,
            0f,
            Animation.ABSOLUTE,
            0f,
            Animation.ABSOLUTE,
            -h.toFloat()
        )

        outAnimationSet.addAnimation(alphaAnimation)
        outAnimationSet.addAnimation(translateAnimation)
        outAnimationSet.duration = DURATION
        inAnimationSet.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
                timeJob = scope?.get()?.launch(Dispatchers.Default) {
                    delay(SCHEDULE_PERIOD)
                    withContext(Dispatchers.Main) {
                        showNextView()
                    }
                }
            }

            override fun onAnimationStart(animation: Animation?) {
            }
        })
        inAnimation = inAnimationSet
        outAnimation = outAnimationSet
    }

    override fun reset() {
        super.reset()
        timeJob?.cancel()
        timeJob = null
    }

    override fun setData(list: List<SearchHintBean>?, scope: LifecycleCoroutineScope) {
        super.setData(list, scope)

        if (list.isNullOrEmpty()) {
            data.add(emptyBean)
        } else {
            data.addAll(list)
        }

        if (data.size == 1) {
            currentIndex = 0
            (currentView as? TextView)?.let { view ->
                getCurrentItemData()?.let { entity ->
                    bindView(entity, view)
                }
                view.show()
            }
        } else {
            showNextView()
        }
    }

    override fun makeView(): View {
        val tv = TextView(context)
        tv.textSize = searchTextSize
        tv.paint.isFakeBoldText = searchTextBold
        val params = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        tv.ellipsize = TextUtils.TruncateAt.END
        tv.setSingleLine()
        tv.layoutParams = params
        tv.gravity = Gravity.CENTER
        tv.setTextColor(ResourceUtil.getColor(R.color.搜索灰))
        return tv
    }

    override fun bindView(entity: SearchHintBean, view: TextView) {
        view.text = entity.displayKeyword
    }
}