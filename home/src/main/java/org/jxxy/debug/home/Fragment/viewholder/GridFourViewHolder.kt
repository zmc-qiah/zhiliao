package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.home.Fragment.adapter.GridFourAdapter
import org.jxxy.debug.home.Fragment.item.GridFour

import org.jxxy.debug.home.databinding.ComponentNewsBinding

class GridFourViewHolder (val binding: ComponentNewsBinding) :
    MultipleViewHolder<GridFour>(binding.root) {
    private val adapter = GridFourAdapter()

    init {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 4)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(10f, 16f, 4))
        binding.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: GridFour) {
        binding.newsTitle.gone()
        entity.grid4Infos?.let { List ->
            adapter.clear()
            val tools = GridFour(entity.type,entity.typeName)
            for (i in 1..80) {
                adapter.add(tools)
            }
        }
    }
}