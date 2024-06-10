package org.jxxy.debug.member.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jxxy.debug.member.fragment.ForumFragment

class ForumInterfaceAdapter(activiy: FragmentActivity) : FragmentStateAdapter(activiy) {
    override fun getItemCount(): Int = fragmentList.size
    val fragmentList = arrayListOf<Fragment>(
        ForumFragment(0),
        ForumFragment(1)
    )
    override fun createFragment(position: Int): Fragment = fragmentList.get(position)
}
