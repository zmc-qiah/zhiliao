package org.jxxy.debug.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.search.bean.SearchHistory
import org.jxxy.debug.search.bean.SearchMiddleSubject
import org.jxxy.debug.search.databinding.ItemSearchHistoryBinding
import org.jxxy.debug.search.databinding.ItemSearchMidldleBinding
import org.jxxy.debug.search.holder.SearchMiddleHistoryHolder
import org.jxxy.debug.search.holder.SearchMiddleLeftHolder

class SearchHistoryAdapter (list: ArrayList<SearchHistory>) : SingleTypeAdapter<SearchHistory>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder = SearchMiddleHistoryHolder (ItemSearchHistoryBinding.inflate(inflater))
}
