package org.jxxy.debug.resources.activity

import android.R.attr.value
import android.content.Intent
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.adapter.VideoFragmentStateAdapter
import org.jxxy.debug.resources.databinding.ItemTextMoreBinding
import org.jxxy.debug.resources.fragment.RecommendFragment
import org.jxxy.debug.resources.fragment.VideoCommentFragment


class TextMoreActivity:BaseActivity<ItemTextMoreBinding>() {

    companion object {
        val RES = 234
    }
    override fun bindLayout(): ItemTextMoreBinding = ItemTextMoreBinding.inflate(layoutInflater)

    override fun initView() {
        val id = intent.getIntExtra("ResourceId",0)
        if (id == 0) {
            "error".toast()
        }
        val viewPage = view.viewPage
        val tabLayout = view.tabLayout
        val list = listOf<Fragment>(
            RecommendFragment(),
            VideoCommentFragment(id)
        )
        viewPage.adapter = VideoFragmentStateAdapter(list, this)
        val tabList = mutableListOf<String>("推荐资讯", "评论")

        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            tab.text = tabList.get(position)
        }.attach()
    }

    override fun subscribeUi() {
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val resultIntent = Intent()
        setResult(RES, resultIntent)
    }
}