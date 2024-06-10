package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.member.bean.Plan
import org.jxxy.debug.member.databinding.ItemPlanListBinding
import org.jxxy.debug.member.viewHolder.PlanListViewHolder

class PlanListAdapter : SingleTypeAdapter<Plan>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = PlanListViewHolder(ItemPlanListBinding.inflate(inflater))
}
