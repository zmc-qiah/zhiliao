package org.jxxy.debug.society.holder

import android.annotation.SuppressLint
import android.util.Log
import android.view.animation.AlphaAnimation
import android.widget.ImageView
import android.widget.TextView
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.HistoryBean
import org.jxxy.debug.society.databinding.ItemHistory2Binding
import kotlin.reflect.KMutableProperty0


class HistoryHolder(
    view: ItemHistory2Binding,
    private val tv: TextView,
    private val textTv: TextView,
    private val img: ImageView,
    private var thisPosition: KMutableProperty0<Int>,
) :
    SingleViewHolder<ItemHistory2Binding, HistoryBean>(view) {

    @SuppressLint("NotifyDataSetChanged")
    override fun setHolder(entity: HistoryBean) {
        val fadeInAnimation = AlphaAnimation(0f, 1f)
        fadeInAnimation.duration = 1000 // 设置动画持续时间（单位：毫秒）
        fadeInAnimation.fillAfter = true
        view.yearTv.text = entity.year
        Log.i("tag2",entity.text)
        Log.i("tag",entity.imageImg)
        tv.startAnimation(fadeInAnimation)
        itemView.setOnClickListener {
            tv.text = entity.text
            img.load(entity.imageImg, 20)
            textTv.startAnimation(fadeInAnimation)
            tv.startAnimation(fadeInAnimation)
            img.startAnimation(fadeInAnimation)

            thisPosition.set(layoutPosition)
            bindingAdapter?.notifyDataSetChanged()
        }
    }
}



