package org.jxxy.debug.society.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import navigation
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.society.R
import org.jxxy.debug.society.bean.MovableImgBean

class MovableImgAdapter(private val context: Context, private val list: ArrayList<MovableImgBean>) :
    PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = inflater.inflate(R.layout.item_movableimg, container, false)
        val imageView = view.findViewById<ImageView>(R.id.movableImg)
        imageView.singleClick {
          list[position].scheme.navigation(context)
        }
        imageView.load(list[position].getValue(),20)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}