package org.jxxy.debug.resources.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jxxy.debug.resources.fragment.GuideFragment

class GuidePageAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    val list = ArrayList<GuideFragment>()
    override fun getItemCount(): Int {
        return 6
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}