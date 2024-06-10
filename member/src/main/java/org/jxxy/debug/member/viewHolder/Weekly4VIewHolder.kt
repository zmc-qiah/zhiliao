package org.jxxy.debug.member.viewHolder

import android.view.ViewGroup
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.R
import org.jxxy.debug.member.bean.Weekly4Item
import org.jxxy.debug.member.databinding.ItemWeekly4Binding

class Weekly4VIewHolder(val binding: ItemWeekly4Binding) : SingleViewHolder<ItemWeekly4Binding, Weekly4Item>(binding) {

    override fun setHolder(entity: Weekly4Item) {
        when (entity.type) {
            0 -> {
                view.textView.apply {
                    text = entity.name
                    setTextColor(ResourceUtil.getColor(R.color.weekly3_text))
                    textSize = 14f
                    (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = 20
                }
            }
            1 -> {
                view.textView.apply {
                    text = entity.name
                    setTextColor(ResourceUtil.getColor(R.color.task_button))
                    textSize = 24f
                    (layoutParams as ViewGroup.MarginLayoutParams).leftMargin = 40
                }
            }
        }
    }
}
