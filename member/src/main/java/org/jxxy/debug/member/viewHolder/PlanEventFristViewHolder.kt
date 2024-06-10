package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.member.bean.PlanEvent
import org.jxxy.debug.member.databinding.ItemPlanBinding

class PlanEventFristViewHolder(binding: ItemPlanBinding) : PlanEventViewHolder(binding) {
    override fun setHolder(entity: PlanEvent) {
        super.setHolder(entity)
        view.leftLineView.alpha = 0f
    }
}
