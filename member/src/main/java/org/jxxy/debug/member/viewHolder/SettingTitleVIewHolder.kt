package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.member.bean.SettingItem
import org.jxxy.debug.member.databinding.ItemSettingBinding

class SettingTitleVIewHolder(val view: ItemSettingBinding) : MultipleViewHolder<SettingItem>(view.root) {
    override fun setHolder(entity: SettingItem) {
        view.textView.text = entity.name
    }
}
