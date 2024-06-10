package org.jxxy.debug.search.adapter

import android.util.Log
import android.view.LayoutInflater

import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView

import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter

import org.jxxy.debug.search.databinding.ItemSearchEnd0Binding
import org.jxxy.debug.search.databinding.ItemSearchEnd1Binding
import org.jxxy.debug.search.databinding.ItemSearchEnd2Binding
import org.jxxy.debug.search.databinding.ItemSearchEnd3Binding
import org.jxxy.debug.search.holder.SearchEndWikiHolder
import org.jxxy.debug.search.holder.SearchEndBookHolder
import org.jxxy.debug.search.holder.SearchEndProblemHolder
import org.jxxy.debug.search.holder.SearchEndNotebookHolder


class SearchEndAdapter2(private val parentFragmentManager: FragmentManager) : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {

        return when (viewType) {

            0 -> SearchEndWikiHolder(ItemSearchEnd0Binding.inflate(inflater))
            1 -> SearchEndBookHolder(ItemSearchEnd1Binding.inflate(inflater))
            2 -> SearchEndProblemHolder(ItemSearchEnd2Binding.inflate(inflater))
            3 -> SearchEndNotebookHolder(ItemSearchEnd3Binding.inflate(inflater), parentFragmentManager)
            else -> null
        }
    }


}