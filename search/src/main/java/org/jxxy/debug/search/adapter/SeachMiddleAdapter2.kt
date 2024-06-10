package org.jxxy.debug.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.search.bean.SearchMiddleTopic
import org.jxxy.debug.search.databinding.ItemSearchMiddle2Binding

import org.jxxy.debug.search.holder.SearchMiddleRightHolder

class SeachMiddleAdapter2 (list: ArrayList<SearchMiddleTopic>) : SingleTypeAdapter<SearchMiddleTopic>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder = SearchMiddleRightHolder(ItemSearchMiddle2Binding.inflate(inflater))
}
