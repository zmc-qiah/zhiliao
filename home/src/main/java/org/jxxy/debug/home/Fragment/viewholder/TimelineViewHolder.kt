package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.home.Fragment.item.bean.HistoryBean
import org.jxxy.debug.home.databinding.ComponentHistoryBinding

class TimelineViewHolder(val binding: ComponentHistoryBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun setHolder(entity: HistoryBean) {
        // 设置时间轴数据
        // 注意：这里只设置了时间轴，不设置历史项数据
    }
}