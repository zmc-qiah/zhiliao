package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.member.bean.PointTask
import org.jxxy.debug.member.databinding.ItemPointTaskBinding
import org.jxxy.debug.member.viewHolder.PointTaskViewHolder

class PointTaskAdapter : SingleTypeAdapter<PointTask>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = PointTaskViewHolder(ItemPointTaskBinding.inflate(inflater))
}
