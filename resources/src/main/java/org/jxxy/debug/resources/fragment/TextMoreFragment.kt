package org.jxxy.debug.resources.fragment

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.resources.adapter.VideoFragmentStateAdapter
import org.jxxy.debug.resources.databinding.FragmentTextMoreBinding
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel

class TextMoreFragment(val id1: Int):BaseFragment<FragmentTextMoreBinding>() {
    private val viewModel: ResourceViewModel by lazy {
        ViewModelProvider(this).get(ResourceViewModel::class.java)
    }
    override fun bindLayout(): FragmentTextMoreBinding = FragmentTextMoreBinding.inflate(layoutInflater)
    override fun initView() {
        val viewPage = find.viewPage
        val tabLayout = find.tabLayout
        val list = listOf<Fragment>(
            RecommendFragment(),
            VideoCommentFragment(id1)
        )
        viewPage.adapter = VideoFragmentStateAdapter(list, requireActivity())
        val tabList = mutableListOf<String>("推荐资讯", "评论")

        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList.get(position)
        }.attach()
    }

    override fun subscribeUi() {
    }
}