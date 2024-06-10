package org.jxxy.debug.home.Fragment.viewholder

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ScheduleTask
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.bean.CarouselBean
import org.jxxy.debug.home.Fragment.item.bean.Carousell
import org.jxxy.debug.home.R
import org.jxxy.debug.home.databinding.ItemCarouselZmcBinding
import org.jxxy.debug.home.databinding.ItemPictureCarouselBinding

import java.util.concurrent.TimeUnit

class CarouseVH(binding: ItemCarouselZmcBinding, private val lifecycle: Lifecycle) :
    MultipleViewHolder2<ItemCarouselZmcBinding, Carousell>(binding) {
    var p = 0
    var oldp = 0
    override fun setHolder(entity: Carousell) {
        view.dotsCarousel.removeAllViews()
        view.rv.setNestedScrollingEnabled(true);
        var dotView: View
        var layoutParams: LinearLayout.LayoutParams
        entity.carousel?.forEachIndexed { index, carouselBean ->
            dotView = View(context)
            dotView.setBackgroundResource(R.drawable.dot)
            layoutParams = LinearLayout.LayoutParams(10, 10)
            if (index != 0) {
                layoutParams.leftMargin = 10
            }
            dotView.isEnabled = false
            view.dotsCarousel.addView(dotView, layoutParams)
        }
        view.tvCarousel.text = entity.carousel?.getOrNull(0)?.title
        view.rv.adapter = object : SingleTypeAdapter<CarouselBean>(){
            override fun createViewHolder(
                viewType: Int,
                inflater: LayoutInflater,
                parent: ViewGroup
            ): RecyclerView.ViewHolder? = object :SingleViewHolder<ItemPictureCarouselBinding,CarouselBean>(
                ItemPictureCarouselBinding.inflate(
                    inflater,parent,false
                )
            ){
                override fun setHolder(entity: CarouselBean) {
                    view.root.singleClick {
                        entity.scheme?.navigation(context)
                    }
                    view.aIv.load(entity.photo,6)
                }
            }
        }.apply {
            add(entity.carousel)
        }
        view.rv.registerOnPageChangeCallback(
            object :OnPageChangeCallback(){
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    view.tvCarousel.text = entity.carousel?.getOrNull(position)?.title
                    view.dotsCarousel.getChildAt(oldp).isEnabled = false
                    view.dotsCarousel.getChildAt(position).isEnabled = true
                }
            }
        )
        ScheduleTask(lifecycle, 5, TimeUnit.SECONDS) {
            oldp = p%(entity.carousel?.size?:1)
            view.rv.currentItem = ((p++)%(entity.carousel?.size?:1))
            Log.d("carouse--subscriaabeUi2", "currentItem ${p}")
        }.startTask()
    }
}