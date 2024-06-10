package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.member.bean.Follow
import org.jxxy.debug.member.databinding.ItemFollowItemBinding
import org.jxxy.debug.member.viewHolder.FollowViewHolder

class FollowAdapter() : SingleTypeAdapter<Follow>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = FollowViewHolder(ItemFollowItemBinding.inflate(inflater))
}
