package org.jxxy.debug.member.viewHolder

import android.util.Log
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.R
import org.jxxy.debug.member.bean.PlanEvent
import org.jxxy.debug.member.databinding.ItemPlanBinding

open class PlanEventViewHolder(binding: ItemPlanBinding) : MultipleViewHolder2<ItemPlanBinding, PlanEvent>(binding) {
    private val TAG = "PlanEventViewHolder"
    override fun setHolder(entity: PlanEvent) {
        Log.d(TAG, "setHolder: " + entity.toString())
        view.planNameTV.text = entity.eventName
        view.planDateTV.text = entity.eventTime
        // 0未开始，1进行中，2已完成
        when (entity.eventState?.toInt()) {
            0 -> {
                view.planPointIcon.setTextColor(ResourceUtil.getColor(R.color.dark_grey))
            }
            1 -> {
                view.planPointIcon.setTextColor(ResourceUtil.getColor(R.color.task_button))
            }
            2 -> {
                view.planPointIcon.setTextColor(ResourceUtil.getColor(R.color.green))
            }
        }
    }
}
