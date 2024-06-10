package org.jxxy.debug.member.activity

import android.graphics.Color
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.R
import org.jxxy.debug.member.adapter.ResourcesViewPageAdapter
import org.jxxy.debug.member.databinding.ActivityResources2Binding
import org.jxxy.debug.member.databinding.Tab1Binding

class ResourcesActiviy() : BaseActivity<ActivityResources2Binding>() {
    override fun bindLayout(): ActivityResources2Binding {
        return ActivityResources2Binding.inflate(layoutInflater)
    }

    override fun initView() {
        val intent = intent
        val page = getPage(intent.getStringExtra("page")!!)
        val tabLayout = view.userResourcesTab
        val viewPage = view.userResourcesView
        viewPage.adapter = ResourcesViewPageAdapter(this)
        tabLayout.setTabTextColors(Color.BLACK, Color.RED)
        TabLayoutMediator(tabLayout, viewPage) { tab, position ->
            val binding = Tab1Binding.inflate(layoutInflater,tabLayout,false)
            tab.customView = binding.root
            tab.tag = binding
            binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.white))
            when (position) {
                0->{
                    binding.tabIcon.text =ResourceUtil.getString(R.string.follow)
                    binding.tabIcon.setTextColor( ResourceUtil.getColor(R.color.point_week_change_begin))
                }
                1->{
                    binding.tabIcon.text =ResourceUtil.getString(org.jxxy.debug.common.R.string.mark)
                    binding.tabIcon.setTextColor(ResourceUtil.getColor(R.color.point_week_change_end))
                }
                2->{
                    binding.tabIcon.text =ResourceUtil.getString(org.jxxy.debug.common.R.string.icon_text_select_note)
                    binding.tabIcon.setTextColor(ResourceUtil.getColor(R.color.bar4))
                }3->{
                    binding.tabIcon.text = ResourceUtil.getString(R.string.history)
                    binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200))
                }
            }
           binding.tabTextView.text = tabList.get(position)
        }.attach()

      tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
          override fun onTabSelected(tab: TabLayout.Tab?) {
              val binding = tab?.tag as Tab1Binding
              when (tab.position) {
                  0->{
                      binding.tabIcon.setTextColor( ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
                      binding.tabTextView.setTextColor( ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
                  }
                  1->{
                      binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.colorYellow))
                      binding.tabTextView.setTextColor( ResourceUtil.getColor(org.jxxy.debug.common.R.color.colorYellow))

                  }
                  2->{
                      binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.red))
                      binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.red))
                  }
                  3->{
                      binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                      binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                  }
              }
          }

          override fun onTabUnselected(tab: TabLayout.Tab?) {
              val binding = tab?.tag as Tab1Binding
              binding.tabTextView.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.white))
              when (tab.position) {
                  0->{
                      binding.tabIcon.setTextColor( ResourceUtil.getColor(R.color.point_week_change_begin))
                  }
                  1->{
                      binding.tabIcon.setTextColor(ResourceUtil.getColor(R.color.point_week_change_end))
                  }
                  2->{
                      binding.tabIcon.setTextColor(ResourceUtil.getColor(R.color.bar4))
                  }3->{
                  binding.tabIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_200))
              }
              }
          }

          override fun onTabReselected(tab: TabLayout.Tab?) {

          }
      })
//

//
//
//          }
//
//          override fun onTabReselected(tab: TabLayout.Tab?) {
//          }
//
//      })
        viewPage.setCurrentItem(page)
        tabLayout.selectTab(tabLayout.getTabAt(page))
    }

    override fun subscribeUi() {
    }
    fun getPage(page: String): Int = when (true) {
        page.contains("订阅" )-> 0
        page.contains("收藏" ) -> 1
        page.contains("笔记" ) -> 2
        page.contains("历史" ) -> 3
        else -> 0
    }
    val tabList = listOf("订阅", "收藏", "笔记", "历史")
}
