package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.History
import org.jxxy.debug.home.databinding.ItemHistoryBinding

class HistoryItemViewHolder(val binding: ItemHistoryBinding) :
    SingleViewHolder<ItemHistoryBinding, History>(binding){


    override fun setHolder(entity: History) {
        if (entity.aiHistory?.size ?: 0 > 1) {
            binding.planDateTV.text = entity.aiHistory?.get(0)?.pointInTime
            binding.planNameTV.text = entity.aiHistory?.get(0)?.historyEvent

            binding.root.singleClick {
                entity.scheme?.navigation(context)
            }
        }
    }
}