package org.jxxy.debug.member.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.member.databinding.ItemPlanBinding
import org.jxxy.debug.member.viewHolder.PlanEventFristViewHolder
import org.jxxy.debug.member.viewHolder.PlanEventLastViewHolder
import org.jxxy.debug.member.viewHolder.PlanEventViewHolder
import org.jxxy.debug.member.viewHolder.SinglePlanEventVIewHolder

class PlanAdapter() : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (viewType) {
        0 -> {
            PlanEventFristViewHolder(ItemPlanBinding.inflate(inflater))
        }
        1 -> {
            PlanEventViewHolder(ItemPlanBinding.inflate(inflater))
        }
        2 -> {
            PlanEventLastViewHolder(ItemPlanBinding.inflate(inflater))
        }
        3 -> {
            SinglePlanEventVIewHolder(ItemPlanBinding.inflate(inflater))
        }
        else -> null
    }
}
