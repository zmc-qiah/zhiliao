package org.jxxy.debug.common.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.AnimationDrawable
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import org.jxxy.debug.common.R


/**
 * Created by DELL on 2017/7/25.
 */
class SmileView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    LinearLayout(context, attrs, defStyleAttr), Animator.AnimatorListener {
    //分割线间距
    private var dividerMargin = 20
    private var count = 0f
    private var defalutBottom = 70
    private var defaultLike = "喜欢"
    private var defalutDis = "无感"
    private val defalutTextColor = Color.WHITE
    private val defaluteShadow = "#7F484848"
    private var defalutGravity = Gravity.CENTER_HORIZONTAL
    private var defalutSize = dip2px(getContext(), 25f)
    private var like = 20
    private var disLike = 20 //点赞数,差评数
    private var fLike = 0f
    private var fDis = 0f
    private var imageLike: ImageView? = null
    private var imageDis: ImageView? = null
    private var likeNum: TextView? = null
    private var disNum: TextView? = null
    private var likeText: TextView? = null
    private var disText: TextView? = null
    private var likeBack: LinearLayout? = null
    private var disBack: LinearLayout? = null
    private var likeAll: LinearLayout? = null
    private var disAll: LinearLayout? = null
    private var animLike: AnimationDrawable? = null
    private var animDis: AnimationDrawable? = null //笑脸帧动画
    private var animatorBack: ValueAnimator? = null //背景拉伸动画
    private var type = 0 //选择执行帧动画的笑脸 //0 笑脸 1 哭脸
    private var isClose = false //判断收起动画
    fun setDefalutBottom(defalutBottom: Int): SmileView {
        this.defalutBottom = defalutBottom
        return this
    }

    fun notifyChange() {
        init()
        bindListener()
    }

    fun setDefalutGravity(defalutGravity: Int) {
        this.defalutGravity = defalutGravity
    }

    fun setDefalutDis(defalutDis: String) {
        this.defalutDis = defalutDis
    }

    fun setDefaultLike(defaultLike: String) {
        this.defaultLike = defaultLike
    }

    fun setDividerMargin(dividerMargin: Int): SmileView {
        this.dividerMargin = dividerMargin
        return this
    }

    fun setDefalutSize(defalutSize: Int) {
        this.defalutSize = defalutSize
    }

    fun setNum(like: Int, dislike: Int) {
        //设置百分比
        count = (like + dislike).toFloat()
        fLike = like / count
        fDis = dislike / count
        this.like = (fLike * 100).toInt()
        disLike = 100 - this.like
        setLike(this.like)
        setDisLike(disLike)
    }

    fun setLike(like: Int) {
        likeNum!!.text = like.toString() + ""
    }

    fun setDisLike(disLike: Int) {
        disNum!!.text = disLike.toString() + ""
    }

    init {
        init()
        bindListener()
    }

    private fun init() {
        removeAllViews()
        //初始化总布局
        orientation = HORIZONTAL
        gravity = defalutGravity or Gravity.BOTTOM
        setBackgroundColor(Color.TRANSPARENT) //开始透明


        //设置百分比
        val count = (like + disLike).toFloat()
        fLike = like / count
        fDis = disLike / count
        like = (fLike * 100).toInt()
        disLike = (fDis * 100).toInt()

        //初始化图片
        imageLike = ImageView(context)
        //添加动画资源  获得帧动画
        imageLike!!.setBackgroundResource(R.drawable.animation_like)
        animLike = imageLike!!.background as AnimationDrawable
        //初始化文字
        likeNum = TextView(context)
        likeNum!!.text = like.toString() + ""
        likeNum!!.setTextColor(defalutTextColor)
        val likeNumPaint = likeNum!!.paint
        likeNumPaint.isFakeBoldText = true
        likeNum!!.textSize = 20f
        likeText = TextView(context)
        likeText!!.text = defaultLike
        likeText!!.setTextColor(defalutTextColor)
        imageDis = ImageView(context)
        imageDis!!.setBackgroundResource(R.drawable.animation_dislike)
        animDis = imageDis!!.background as AnimationDrawable
        disNum = TextView(context)
        disNum!!.text = disLike.toString() + ""
        disNum!!.setTextColor(defalutTextColor)
        val disNumPaint = disNum!!.paint
        disNumPaint.isFakeBoldText = true
        disNum!!.textSize = 20f
        disText = TextView(context)
        disText!!.text = defalutDis
        disText!!.setTextColor(defalutTextColor)


        //初始化布局
        likeBack = LinearLayout(context)
        disBack = LinearLayout(context)
        val params2 = LayoutParams(defalutSize, defalutSize)
        likeBack!!.addView(imageLike, params2)
        disBack!!.addView(imageDis, params2)
        disBack!!.setBackgroundResource(R.drawable.white_background)
        likeBack!!.setBackgroundResource(R.drawable.white_background)

        //单列总布局
        likeAll = LinearLayout(context)
        disAll = LinearLayout(context)
        likeAll!!.orientation = VERTICAL
        disAll!!.orientation = VERTICAL
        likeAll!!.gravity = Gravity.CENTER_HORIZONTAL
        disAll!!.gravity = Gravity.CENTER_HORIZONTAL
        likeAll!!.setBackgroundColor(Color.TRANSPARENT)
        disAll!!.setBackgroundColor(Color.TRANSPARENT)

        //添加文字图片放进一列
        val params =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params.setMargins(0, 10, 0, 0)
        params.gravity = Gravity.CENTER
        disAll!!.gravity = Gravity.CENTER_HORIZONTAL
        likeAll!!.gravity = Gravity.RIGHT
        disAll!!.addView(disNum, params)
        disAll!!.addView(disText, params)
        disAll!!.addView(disBack, params)
        likeAll!!.addView(likeNum, params)
        likeAll!!.addView(likeText, params)
        likeAll!!.addView(likeBack, params)

        //中间分隔线
        val imageView = ImageView(context)
        imageView.background = ColorDrawable(Color.GRAY)
        val params4 = LayoutParams(3, 80)
        params4.setMargins(dividerMargin, 10, dividerMargin, defalutBottom + 20)
        params4.gravity = Gravity.BOTTOM
        val params3 =
            LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        params3.setMargins(30, 20, 30, defalutBottom)
        params3.gravity = Gravity.BOTTOM
        addView(disAll, params3)
        addView(imageView, params4)
        addView(likeAll, params3)

        //隐藏文字
        setVisibities(GONE)
    }

    //
    fun setVisibities(v: Int) {
        likeNum!!.visibility = v
        disNum!!.visibility = v
        likeText!!.visibility = v
        disText!!.visibility = v
    }

    //绑定监听
    private fun bindListener() {
        imageDis!!.setOnClickListener { /* type = 1; //设置动画对象
                    animBack(); //拉伸背景
                    setVisibities(VISIBLE); //隐藏文字
                    //切换背景色
                    setBackgroundColor(Color.TRANSPARENT);
                    likeBack.setBackgroundResource(R.drawable.white_background);
                    disBack.setBackgroundResource(R.drawable.yellow_background);
                    //重置帧动画
                    imageLike.setBackground(null);
                    imageLike.setBackgroundResource(R.drawable.animation_like);
                    animLike = (AnimationDrawable) imageLike.getBackground();*/
            disLikeAnimation()
        }
        imageLike!!.setOnClickListener { /* type = 0;
                     animBack();
                     setVisibities(VISIBLE);
                     setBackgroundColor(Color.TRANSPARENT);
                     disBack.setBackgroundResource(R.drawable.white_background);
                     likeBack.setBackgroundResource(R.drawable.yellow_background);
                     imageDis.setBackground(null);
                     imageDis.setBackgroundResource(R.drawable.animation_dislike);
                     animDis = (AnimationDrawable) imageDis.getBackground();*/
            likeAnimation()
        }
    }

    fun disLikeAnimation() {
        type = 1 //设置动画对象
        animBack() //拉伸背景
        setVisibities(VISIBLE) //隐藏文字
        //切换背景色
        setBackgroundColor(Color.parseColor(defaluteShadow))
        likeBack!!.setBackgroundResource(R.drawable.white_background)
        disBack!!.setBackgroundResource(R.drawable.yellow_background)
        //重置帧动画
        imageLike!!.background = null
        imageLike!!.setBackgroundResource(R.drawable.animation_like)
        animLike = imageLike!!.background as AnimationDrawable
    }

    fun likeAnimation() {
        type = 0
        animBack()
        setVisibities(VISIBLE)
        setBackgroundColor(Color.parseColor(defaluteShadow))
        disBack!!.setBackgroundResource(R.drawable.white_background)
        likeBack!!.setBackgroundResource(R.drawable.yellow_background)
        imageDis!!.background = null
        imageDis!!.setBackgroundResource(R.drawable.animation_dislike)
        animDis = imageDis!!.background as AnimationDrawable
    }

    //背景伸展动画
    fun animBack() {
        //动画执行中不能点击
        imageDis!!.isClickable = false
        imageLike!!.isClickable = false
        val max = Math.max(like * 4, disLike * 4)
        animatorBack = ValueAnimator.ofInt(5, max)
        animatorBack?.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            val magrin = animation.animatedValue as Int
            val paramsLike = imageLike!!.layoutParams as LayoutParams
            paramsLike.bottomMargin = magrin
            if (magrin <= like * 4) {
                imageLike!!.layoutParams = paramsLike
            }
            if (magrin <= disLike * 4) {
                imageDis!!.layoutParams = paramsLike
            }
        })
        isClose = false
        animatorBack?.addListener(this)
        animatorBack?.setDuration(500)
        animatorBack?.start()
    }

    //背景收回动画
    fun setBackUp() {
        val max = Math.max(like * 4, disLike * 4)
        animatorBack = ValueAnimator.ofInt(max, 5)
        animatorBack?.addUpdateListener(ValueAnimator.AnimatorUpdateListener { animation ->
            val magrin = animation.animatedValue as Int
            val paramsLike = imageLike!!.layoutParams as LayoutParams
            paramsLike.bottomMargin = magrin
            if (magrin <= like * 4) {
                imageLike!!.layoutParams = paramsLike
            }
            if (magrin <= disLike * 4) {
                imageDis!!.layoutParams = paramsLike
            }
        })
        animatorBack?.addListener(this)
        animatorBack?.setDuration(500)
        animatorBack?.start()
    }

    override fun onAnimationEnd(animation: Animator) {
        //重置帧动画
        animDis!!.stop()
        animLike!!.stop()

        //关闭时不执行帧动画
        if (isClose) {
            //收回后可点击
            imageDis!!.isClickable = true
            imageLike!!.isClickable = true
            //隐藏文字
            setVisibities(GONE)
            //恢复透明
            setBackgroundColor(Color.TRANSPARENT)
            return
        }
        isClose = true
        if (type == 0) {
            animLike!!.start()
            objectY(imageLike)
        } else {
            animDis!!.start()
            objectX(imageDis)
        }
    }

    fun objectY(view: View?) {
        val animator = ObjectAnimator.ofFloat(
            view,
            "translationY",
            -10.0f,
            0.0f,
            10.0f,
            0.0f,
            -10.0f,
            0.0f,
            10.0f,
            0f
        )
        animator.repeatMode = ObjectAnimator.RESTART
        //animator.setRepeatCount(1);
        animator.duration = 1500
        animator.start()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                setBackUp() //执行回弹动画
            }
        })
    }

    fun objectX(view: View?) {
        val animator = ObjectAnimator.ofFloat(
            view,
            "translationX",
            -10.0f,
            0.0f,
            10.0f,
            0.0f,
            -10.0f,
            0.0f,
            10.0f,
            0f
        )
        animator.repeatMode = ObjectAnimator.RESTART
        // animator.setRepeatCount(1);
        animator.duration = 1500
        animator.start()
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                setBackUp() //执行回弹动画
            }
        })
    }

    override fun onAnimationStart(animation: Animator) {}
    override fun onAnimationCancel(animation: Animator) {}
    override fun onAnimationRepeat(animation: Animator) {}

    companion object {
        //dp转px
        fun dip2px(context: Context, dipValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (dipValue * scale + 0.5f).toInt()
        }

        //px转dp
        fun px2dip(context: Context, pxValue: Float): Int {
            val scale = context.resources.displayMetrics.density
            return (pxValue / scale + 0.5f).toInt()
        }
    }
}