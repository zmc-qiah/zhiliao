package org.jxxy.debug.theme.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jxxy.debug.theme.fragment.GuideFragment

class GuidePageAdapter(fragment : FragmentActivity) : FragmentStateAdapter(fragment) {
    val list = ArrayList<GuideFragment>()
    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }
}