package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.bean.HotlistBean
import org.jxxy.debug.society.databinding.ItemHotlistBinding
import org.jxxy.debug.society.holder.HotlistHolder

class HotlistAdapter (list: ArrayList<HotlistBean>) : SingleTypeAdapter<HotlistBean>() {

    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup,
    ): RecyclerView.ViewHolder =
        HotlistHolder (ItemHotlistBinding.inflate(inflater))
}