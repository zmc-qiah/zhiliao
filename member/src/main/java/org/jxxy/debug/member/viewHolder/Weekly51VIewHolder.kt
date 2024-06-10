package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.member.bean.Weekly5Item
import org.jxxy.debug.member.databinding.ItemWeekly51Binding

class Weekly51VIewHolder(val binding: ItemWeekly51Binding) : MultipleViewHolder<Weekly5Item>(binding.root) {
    override fun setHolder(entity: Weekly5Item) {
        binding.aa.text = 78f.toString()
    }
}
