package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.PopularizeAdapter
import org.jxxy.debug.home.Fragment.item.Popularize
import org.jxxy.debug.home.databinding.ComponentBinding

class PopularizeViewHolder(binding: ComponentBinding) :
    SingleViewHolder<ComponentBinding, Popularize>(binding) {
        private val adapter = PopularizeAdapter()

        init {
            binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 2)
            binding.recyclerview.addItemDecoration(SpanItemDecoration(12f, 14f, 2))
            binding.recyclerview.adapter = adapter
        }

        override fun setHolder(entity: Popularize) {
            entity.quickEntryInfos?.let { List ->
                adapter.clear()
                val tools = Popularize(entity.type, entity.typeName,List)
                for (i in 1..80) {
                    adapter.add(tools)
                }
            }
        }
    }