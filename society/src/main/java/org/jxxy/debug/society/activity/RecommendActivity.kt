package org.jxxy.debug.society.activity

import android.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.R
import org.jxxy.debug.society.databinding.ActivityRecommendBinding
import org.jxxy.debug.society.fragment.HotFragment
import org.jxxy.debug.society.fragment.HotlistFragment

class RecommendActivity:BaseActivity<ActivityRecommendBinding>() {
    override fun bindLayout(): ActivityRecommendBinding {
        return ActivityRecommendBinding.inflate(layoutInflater)
    }
    override fun initView() {

        view.recommendVp.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                // 页面选中时的回调，可以在这里更新背景
                updateBackground(position)
            }
        })

        view.recommendVp.adapter=object : FragmentStateAdapter(this){
            override fun getItemCount()=2
            override fun createFragment(position: Int): Fragment {
                return when(position){
                    0-> HotFragment()
                    1-> HotlistFragment()
                    else -> HotFragment()
                }
            }
        }
        TabLayoutMediator(view.recommendTl ,view.recommendVp){tab,position->
            when(position){
                0->tab.text= "热门讨论"
                1->tab.text="新鲜咨询"
            }
        }.attach()
    }
    override fun subscribeUi() {

    }
    private fun updateBackground(position: Int) {
        when (position) {
            0 -> {
                ContextCompat.getDrawable(view.recommendVp.context, R.drawable.background_taobao2)
                // 第一页的背景设置
            }
            1 -> {
                ContextCompat.getDrawable(view.recommendVp.context, R.drawable.background_discuss_answer)
                // 第二页的背景设置
            }

        }
    }
}