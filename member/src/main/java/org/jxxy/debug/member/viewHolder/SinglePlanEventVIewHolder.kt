package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.member.bean.PlanEvent
import org.jxxy.debug.member.databinding.ItemPlanBinding

class SinglePlanEventVIewHolder(binding: ItemPlanBinding) : PlanEventViewHolder(binding) {
    override fun setHolder(entity: PlanEvent) {
        super.setHolder(entity)
        view.leftLineView.alpha = 0f
        view.rightLineView.alpha = 0f
    }
}
