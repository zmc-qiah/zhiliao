package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.member.databinding.ItemWeekly51Binding
import org.jxxy.debug.member.databinding.ItemWeekly52Binding
import org.jxxy.debug.member.viewHolder.Weekly51VIewHolder
import org.jxxy.debug.member.viewHolder.Weekly52VIewHolder

class Weekly5Adapter : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (viewType) {
        0 -> Weekly51VIewHolder(ItemWeekly51Binding.inflate(inflater))
        1 -> Weekly52VIewHolder(ItemWeekly52Binding.inflate(inflater))
        else -> null
    }
}
