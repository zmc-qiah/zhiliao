package org.jxxy.debug.member.viewHolder

import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.R
import org.jxxy.debug.member.adapter.ForumInterfaceAdapter
import org.jxxy.debug.member.databinding.ItemForumInterfaceBinding
import org.jxxy.debug.member.databinding.Tab1Binding
import org.jxxy.debug.member.http.respone.GroupForumResponse

class MemberForumViewHolder(binding: ItemForumInterfaceBinding, val activity: FragmentActivity, private val onClick: (view: View) -> Unit) : MultipleViewHolder2<ItemForumInterfaceBinding, GroupForumResponse>(binding) {

    override fun setHolder(entity: GroupForumResponse) {
        val adapter = ForumInterfaceAdapter(activity)
        view.forumViewPage.adapter = adapter
        val tabList = listOf("圈子消息", "我的动态")
        view.root.setTag(org.jxxy.debug.common.R.id.nested_child_item_container, java.lang.Boolean.TRUE)
        TabLayoutMediator(view.forumTab, view.forumViewPage) { tab, position ->
            val binding = Tab1Binding.inflate(
                LayoutInflater.from(view.forumTab.context),
                view.forumTab,
                false
            )
            tab.customView = binding.root
            tab.tag = binding
            binding.tabTextView.text = tabList.get(position)
            when (position) {
                0->{
                    binding.tabIcon.text =ResourceUtil.getString(R.string.groupMessage)
                    binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                    binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                }
                1->{
                    binding.tabIcon.text =ResourceUtil.getString(R.string.MyMessage)
                    binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
                    binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
                }
            }
        }.attach()
        view.forumTab.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                val binding = tab?.tag as Tab1Binding
                binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                val binding = tab?.tag as Tab1Binding
                binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
                binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

        })

    }
}
