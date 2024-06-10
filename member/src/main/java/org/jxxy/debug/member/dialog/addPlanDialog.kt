package org.jxxy.debug.member.dialog

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import org.jxxy.debug.corekit.common.BaseDialog
import org.jxxy.debug.corekit.util.TimeUtils
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.member.R
import org.jxxy.debug.member.bean.Plan
import org.jxxy.debug.member.bean.PlanEvent
import org.jxxy.debug.member.databinding.DialogAddPlanBinding
import org.jxxy.debug.member.http.viewModel.MemberViewModel

class addPlanDialog(val viewModel: MemberViewModel, val plan: Plan=Plan(), var type: Int = 0) : BaseDialog<DialogAddPlanBinding>() {
    val tmep = when (type) {
        0 -> "学习计划"
        1 -> "学习事件"
        else -> { "" }
    }
    init {
        enableBack = true
    }
    override fun getView(inflater: LayoutInflater, parent: ViewGroup?): DialogAddPlanBinding =
        DialogAddPlanBinding.inflate(layoutInflater)

    override fun initView(view: DialogAddPlanBinding) {
        view.planEndTimeET.clearComposingText()
        view.planNameET.clearComposingText()
        view.finBtn.singleClick { onclik(it, view) }
        view.addEvenBtn.singleClick { onclik(it, view) }
    }

    private fun onclik(it: Button, view: DialogAddPlanBinding) {
        val name: String = view.planNameET.text.toString()
        val date: String = view.planEndTimeET.text.toString()

        if (name.isBlank()) {
            tmep + "名称不能为空".toast()
            return
        }
        if (date.isBlank()) {
            tmep + "截至时间不能为空".toast()
            return
        }
        if (type == 0) {
            plan.state = 0
            plan.name = name
            plan.startTime = TimeUtils.millis2String(System.currentTimeMillis())
            plan.studyEvent = ArrayList<PlanEvent>()
            plan.endTime = date
        } else if (type == 1) {
            val event = PlanEvent()
            event.eventName = name
            event.eventTime = date
            plan.studyEvent?.add(event)
        }
        Log.d("TAG", "onclik: " + plan)
        if (it.id == R.id.addEvenBtn) {
            view.planEndTimeET.setText("")
            view.planNameET.setText("")
            type = 1
        } else if (it.id == R.id.finBtn) {
            viewModel.addPlan(plan)
            dismiss()
        }
    }
}
