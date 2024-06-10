package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.LearnAdapter
import org.jxxy.debug.home.Fragment.decoration.SlideBarItemDecoration
import org.jxxy.debug.home.Fragment.item.Learn
import org.jxxy.debug.home.databinding.StudyComponentLearnBinding

class LearnViewHolder (val binding: StudyComponentLearnBinding) :
    MultipleViewHolder<Learn>(binding.root) {
    private val adapter = LearnAdapter()

    init {
        val layoutManager = GridLayoutManager(itemView.context, 2)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.addItemDecoration(SpanItemDecoration(0f, 15f, 2))
        binding.recyclerview.addItemDecoration(SlideBarItemDecoration(context))
        binding.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: Learn) {
        binding.learn.text = entity.typeName
        entity.studyInfos?.let { List ->
            adapter.clear()
            val tools = Learn(entity.type,entity.typeName,entity.totalPicture,entity.totalTitle,entity.hot, List)
            for (i in 1..80) {
                adapter.add(tools)
            }
        }
    }
}