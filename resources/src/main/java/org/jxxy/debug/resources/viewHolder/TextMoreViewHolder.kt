package org.jxxy.debug.resources.viewHolder

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.resources.adapter.VideoFragmentStateAdapter
import org.jxxy.debug.resources.bean.ResourceId
import org.jxxy.debug.resources.databinding.ItemTextMoreBinding
import org.jxxy.debug.resources.fragment.RecommendFragment
import org.jxxy.debug.resources.fragment.VideoCommentFragment

class TextMoreViewHolder(binding: ItemTextMoreBinding, val activity: FragmentActivity) : MultipleViewHolder2<ItemTextMoreBinding, ResourceId>(binding) {
    override fun setHolder(entity: ResourceId) {
        val viewPage = view.viewPage
        val tabLayout = view.tabLayout
        view.root.setTag(org.jxxy.debug.common.R.id.nested_child_item_container, java.lang.Boolean.TRUE)
        val list = listOf<Fragment>(
            RecommendFragment(),
            VideoCommentFragment(entity.id)
        )
        viewPage.adapter = VideoFragmentStateAdapter(list, activity)
        val tabList = mutableListOf<String>("推荐资讯", "评论")

        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList.get(position)
        }.attach()
    }
}
