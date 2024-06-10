package org.jxxy.debug.search.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.search.bean.SearchMiddleSubject
import org.jxxy.debug.search.databinding.ItemSearchMidldleBinding
import org.jxxy.debug.search.holder.SearchMiddleLeftHolder

class SearchMiddleAdapter(list: ArrayList<SearchMiddleSubject>) : SingleTypeAdapter<SearchMiddleSubject>() {
    init {
        add(list)
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder = SearchMiddleLeftHolder(ItemSearchMidldleBinding.inflate(inflater))}
