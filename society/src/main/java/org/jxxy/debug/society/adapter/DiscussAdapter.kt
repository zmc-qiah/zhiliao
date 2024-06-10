package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.society.bean.Select
import org.jxxy.debug.society.holder.SelectHolder
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.databinding.ItemSelectBinding

class DiscussAdapter (list: ArrayList<Select>) : SingleTypeAdapter<Select>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder =
        SelectHolder(ItemSelectBinding.inflate(inflater))

}