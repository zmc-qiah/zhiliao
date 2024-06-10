package org.jxxy.debug.member.viewHolder


import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.member.bean.Weekly5Item
import org.jxxy.debug.member.databinding.ItemWeekly52Binding

class Weekly52VIewHolder(val binding: ItemWeekly52Binding) : MultipleViewHolder<Weekly5Item>(binding.root) {
    override fun setHolder(entity: Weekly5Item) {
        binding.weekly5Date.text = entity.date
        binding.weekly5Name.text = entity.name
    }
}