package org.jxxy.debug.society.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.viewpager.widget.PagerAdapter
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.AiImgBean
import org.jxxy.debug.society.R

class PracticeAdapter(private val context: Context, private val list: ArrayList<AiImgBean>) :
    PagerAdapter() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getCount(): Int {
        return list.size
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = inflater.inflate(R.layout.item_practice, container, false)
        val imageView = view.findViewById<ImageView>(R.id.aiImg)

        imageView.load(list[position].getValue(),10)


        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

}