package org.jxxy.debug.member.viewHolder

import android.view.ViewGroup
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.member.R
import org.jxxy.debug.member.bean.SettingItem
import org.jxxy.debug.member.databinding.ItemSetting2Binding

class SettingContentVIewHolder(val view: ItemSetting2Binding): MultipleViewHolder<SettingItem>(view.root) {
    override fun setHolder(entity: SettingItem) {
        view.textView.text = entity.name
    }
}