package org.jxxy.debug.society.activity
import android.util.Log
import android.util.TypedValue
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.society.R
import org.jxxy.debug.society.fragment.HotFragment
import org.jxxy.debug.society.fragment.NewFragment
import org.jxxy.debug.society.fragment.SelectFragmet
import org.jxxy.debug.society.databinding.ActivityDiscussBinding

class DiscussActivity :BaseActivity<ActivityDiscussBinding>() {
    override fun bindLayout(): ActivityDiscussBinding {
        return ActivityDiscussBinding.inflate(layoutInflater)
    }


    override fun initView() {

        view.aiVp.adapter=object :FragmentStateAdapter(this){
            override fun getItemCount()=3
            override fun createFragment(position: Int): Fragment {
                //暂时一样
                return when(position){
                    0-> HotFragment()
                    1-> HotFragment()
                    2-> HotFragment()
                    else -> HotFragment()
                }
            }
        }



        TabLayoutMediator(view.aiTl,view.aiVp){tab,position->
            when(position){
                0->tab.text= "热门讨论"
                1->tab.text="新鲜咨询"
                2->tab.text="精选热帖"
            }
        }.attach()

    }

    override fun subscribeUi() {

    }
}