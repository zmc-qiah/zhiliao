package org.jxxy.debug.home.Fragment.viewholder

import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.InterviewAdapter
import org.jxxy.debug.home.Fragment.item.Interview
import org.jxxy.debug.home.databinding.StudyComponentInterviewBinding

class InterviewViewHolder (val binding: StudyComponentInterviewBinding,private val lifecycle: Lifecycle) :
    MultipleViewHolder<Interview>(binding.root){
    private val adapter= InterviewAdapter()

    init {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 1)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(15f, 10f, 1))
        binding.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: Interview) {
        binding.interview.text = entity.typeName
        entity.let { List ->
            adapter.clear()
            adapter.add(entity)
        }
    }
}