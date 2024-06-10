package org.jxxy.debug.home.Fragment.http.viewmodel

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.lifecycle.Lifecycle
import androidx.viewpager.widget.ViewPager
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.ScheduleTask
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.adapter.CarouselAdapter
import org.jxxy.debug.home.Fragment.item.Carousel
import org.jxxy.debug.home.R
import org.jxxy.debug.home.databinding.ComponentCarouselBinding
import java.util.concurrent.TimeUnit


class CarouselViewHolder(private val binding: ComponentCarouselBinding, private val lifecycle: Lifecycle) :
    MultipleViewHolder<Carousel>(binding.root) {
    override fun setHolder(entity: Carousel) {
        val viewPager: ViewPager = binding.vp
        val mImgList: ArrayList<ImageView> = arrayListOf()
        var imageView: ImageView
        var dotView: View
        var layoutParams: LinearLayout.LayoutParams
        binding.dotsCarousel.removeAllViews()
        entity.carousel?.forEachIndexed { index, carouselBean ->
            imageView = ImageView(context)
            imageView.load(carouselBean.photo)
            imageView.singleClick {
                carouselBean.scheme?.navigation(context)
            }
            mImgList.add(imageView)
            dotView = View(context)
            dotView.setBackgroundResource(R.drawable.dot)
            layoutParams = LinearLayout.LayoutParams(10, 10)
            if (index != 0) {
                layoutParams.leftMargin = 10
            }
            // 设置默认所有都不可用
            dotView.isEnabled = false
            binding.dotsCarousel.addView(dotView, layoutParams)
        }

        binding.tvCarousel.text = entity.carousel?.getOrNull(0)?.title
        var previousSelectedPosition = 0

        // 设置适配器
        viewPager.adapter = CarouselAdapter(mImgList)
        val totalCount =mImgList.size



        val currentPosition: Int = (totalCount+1)%3
        viewPager.currentItem = (currentPosition)

        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageSelected(position: Int) {
                Log.d("ViewPager", "Current position: $position")
                if (mImgList.size>0){
                    val newPosition: Int = position % mImgList.size
                    binding.tvCarousel.text = entity.carousel?.getOrNull(newPosition)?.title
                    binding.dotsCarousel.getChildAt(previousSelectedPosition).isEnabled = false
                    binding.dotsCarousel.getChildAt(newPosition).isEnabled = true
                    previousSelectedPosition = newPosition
                }
            }
            override fun onPageScrollStateChanged(state: Int) {}
        })
        // 轮播
        ScheduleTask(lifecycle, 5, TimeUnit.SECONDS) {
            viewPager.currentItem = (viewPager.currentItem + 1)%mImgList.size
            Log.d("carouse--subscriaabeUi2", "currentItem $currentPosition")
        }.startTask()
    }


}
