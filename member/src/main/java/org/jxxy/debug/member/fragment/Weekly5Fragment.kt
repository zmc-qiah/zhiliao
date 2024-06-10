package org.jxxy.debug.member.fragment

import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.common.BaseFragment
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.member.adapter.Weekly5Adapter
import org.jxxy.debug.member.bean.Weekly5Item
import org.jxxy.debug.member.databinding.FragmentWeekly5Binding
import org.jxxy.debug.member.http.respone.WeekReportResponce

class Weekly5Fragment(val info: WeekReportResponce) : BaseFragment<FragmentWeekly5Binding>() {
    override fun bindLayout(): FragmentWeekly5Binding = FragmentWeekly5Binding.inflate(layoutInflater)
    val adapter by lazy { Weekly5Adapter()}
    override fun initView() {
        find.weekly5.adapter = adapter
        adapter.clear()
        adapter.add(Weekly5Item(0))
        info.weekAchievementInfos?.let {
            it.forEach {
                adapter.add(Weekly5Item(1,"${it.name}\n${it.describe}"))
            }
        }
        adapter.add(Weekly5Item(0))
        find.weekly5.addItemDecoration(CommonItemDecoration(10f, RecyclerView.HORIZONTAL))
    }

    override fun subscribeUi() {

    }
}
