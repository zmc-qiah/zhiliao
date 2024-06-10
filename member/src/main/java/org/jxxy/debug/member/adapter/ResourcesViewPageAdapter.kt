package org.jxxy.debug.member.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jxxy.debug.member.fragment.FollowFragment
import org.jxxy.debug.member.fragment.HistoryFragment
import org.jxxy.debug.member.fragment.MarkFragment
import org.jxxy.debug.member.fragment.NoteFragment

// import com.debug.resources.fragment.testFragment

class ResourcesViewPageAdapter(activiy: AppCompatActivity) : FragmentStateAdapter(activiy) {
    override fun getItemCount(): Int = fragmentList.size
    private val fragmentList = arrayListOf<Fragment>(
        FollowFragment(),
        MarkFragment(),
        NoteFragment(),
        HistoryFragment()
    )

    override fun createFragment(position: Int): Fragment = fragmentList.get(position)
}
