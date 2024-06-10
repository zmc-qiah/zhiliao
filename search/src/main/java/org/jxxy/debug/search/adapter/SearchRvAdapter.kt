package org.jxxy.debug.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.search.bean.SearchMiddleSubject
import org.jxxy.debug.search.bean.SearchRv
import org.jxxy.debug.search.databinding.ItemSearchMidldleBinding
import org.jxxy.debug.search.databinding.ItemSearchrvBinding
import org.jxxy.debug.search.holder.SearchMiddleLeftHolder
import org.jxxy.debug.search.holder.SearchRvHolder

class SearchRvAdapter (list: List<SearchRv>) : SingleTypeAdapter<SearchRv>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder = SearchRvHolder (ItemSearchrvBinding.inflate(inflater))
}
