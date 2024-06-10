package org.jxxy.debug.member.activity

import android.util.Log
import androidx.lifecycle.ViewModelProvider
import org.jxxy.debug.corekit.common.BaseActivity
import org.jxxy.debug.corekit.recyclerview.CommonItemDecoration
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.adapter.PlanListAdapter
import org.jxxy.debug.member.databinding.ActivityPlanBinding
import org.jxxy.debug.member.dialog.addPlanDialog
import org.jxxy.debug.member.http.viewModel.MemberViewModel

class PlanActivity : BaseActivity<ActivityPlanBinding>() {
    val viewModel: MemberViewModel by lazy {
        ViewModelProvider(this).get(MemberViewModel::class.java)
    }
    val adapter: PlanListAdapter by lazy { PlanListAdapter() }
    val TAG = "PlanActivity"
    override fun bindLayout(): ActivityPlanBinding = ActivityPlanBinding.inflate(layoutInflater)

    override fun initView() {
        view.bigTextTV.text = ""
        view.smallText1TV.text = ""
        view.smallText2TV.text = ""
        view.listRecyclerView.adapter = adapter
        view.listRecyclerView.addItemDecoration(CommonItemDecoration(8f))
        viewModel.getPlan()
        view.addPlanBtn.singleClick {
            addPlanDialog(viewModel).show(supportFragmentManager)
        }
    }

    override fun subscribeUi() {
        viewModel.planDataLiveData.observe(this) {
            it?.response?.let { list ->
                Log.d(TAG, "subscribeUi2: $it")
                adapter.clearAndAdd(list)
                view.bigTextTV.text = "${list.size}条计划"
                var planCnt = 0
                var eventCnt = 0
                for (plan in list) {
                    if (plan.state?.toInt() != 2) {
                        planCnt++
                        plan.studyEvent?.forEach {
                            if (it.eventState?.toInt() != 2) {
                                eventCnt++
                            }
                        }
                    }
                }
                view.smallText1TV.text = "剩余$planCnt 条计划"
                view.smallText2TV.text = "剩余$eventCnt 个任务"
            }
        }
        viewModel.addPlanDataLiveData.observe(this) {
            viewModel.getPlan()
        }
    }
}
