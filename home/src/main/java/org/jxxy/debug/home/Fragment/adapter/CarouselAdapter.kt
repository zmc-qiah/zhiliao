package org.jxxy.debug.home.Fragment.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter

class CarouselAdapter(private var imgList: ArrayList<ImageView>):PagerAdapter() {
    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val totalCount =imgList.size
        if (totalCount == 0) {
            return 1
        }
        val newPosition:Int =position%totalCount
        val img:ImageView=imgList.get(newPosition)
        val parent = img.parent as? ViewGroup
        parent?.removeView(img) // 移除原来的父容器
        container.addView(img)
        return img
    }
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        if(`object` is View){
            container.removeView(`object`)
        }
    }

    override fun getCount(): Int {
        return Int.MAX_VALUE
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view==`object`
    }
}