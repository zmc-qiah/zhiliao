package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.member.bean.Weekly4Item
import org.jxxy.debug.member.databinding.ItemWeekly4Binding
import org.jxxy.debug.member.viewHolder.Weekly4VIewHolder

class Weekly4Adapter : SingleTypeAdapter<Weekly4Item>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = Weekly4VIewHolder(ItemWeekly4Binding.inflate(inflater))
}
