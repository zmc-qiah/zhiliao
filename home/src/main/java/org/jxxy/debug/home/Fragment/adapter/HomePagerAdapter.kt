package org.jxxy.debug.home.Fragment.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jxxy.debug.home.Fragment.Listener.aListener

class HomePagerAdapter(activity: FragmentActivity,val fragment:List<Fragment>
,val listener:aListener? = null):FragmentStateAdapter(activity) {
//    private val tabTitles = arrayOf("科普", "教育","实践")
    override fun getItemCount(): Int = fragment.size
    override fun createFragment(position: Int): Fragment = fragment[position]
}