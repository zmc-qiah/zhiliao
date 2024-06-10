package org.jxxy.debug.resources.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class VideoFragmentStateAdapter(val list: List<Fragment>, val activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list.get(position)
}
