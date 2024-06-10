package org.jxxy.debug.test.fragment.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.test.databinding.ActivityAnswerListBinding
import org.jxxy.debug.test.fragment.bean.QuestionContext
import org.jxxy.debug.test.fragment.fragment.AnswerFragment

class AnswerVpAdapter(activity: BaseActivity<ActivityAnswerListBinding>) : FragmentStateAdapter(activity) {

    val fragments : ArrayList<Fragment> = ArrayList()
    init {
        val list: ArrayList<QuestionContext> = ArrayList()
        //5 6 7 8对应4个专题
        fragments.add(AnswerFragment( 5))
        fragments.add(AnswerFragment(6))
        fragments.add(AnswerFragment(7))
        fragments.add(AnswerFragment(8))
    }
    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int): Fragment = fragments.get(position)
}