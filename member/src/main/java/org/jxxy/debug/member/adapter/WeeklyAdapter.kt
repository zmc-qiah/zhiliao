package org.jxxy.debug.member.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jxxy.debug.member.fragment.*

class WeeklyAdapter(val list: List<Fragment>,val activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment = list.get(position)
}
