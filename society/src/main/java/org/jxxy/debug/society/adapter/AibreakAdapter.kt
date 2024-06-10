package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.society.bean.AibreakBean
import org.jxxy.debug.society.databinding.ItemAibreakBinding
import org.jxxy.debug.society.holder.AibreakHolder

class AibreakAdapter(list: ArrayList<AibreakBean>): SingleTypeAdapter<AibreakBean>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder=AibreakHolder (ItemAibreakBinding .inflate(inflater))}


