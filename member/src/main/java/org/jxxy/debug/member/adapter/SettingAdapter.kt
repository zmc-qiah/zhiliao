package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.member.databinding.ItemSetting2Binding
import org.jxxy.debug.member.databinding.ItemSettingBinding
import org.jxxy.debug.member.viewHolder.SettingContentVIewHolder
import org.jxxy.debug.member.viewHolder.SettingTitleVIewHolder

class SettingAdapter : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        var viewHolder: RecyclerView.ViewHolder? = when (viewType) {
            0 -> {
                SettingTitleVIewHolder(ItemSettingBinding.inflate(inflater))
            }
            1 -> {
                SettingContentVIewHolder(ItemSetting2Binding.inflate(inflater))
            }
            else -> null
        }
        return viewHolder
    }

}
