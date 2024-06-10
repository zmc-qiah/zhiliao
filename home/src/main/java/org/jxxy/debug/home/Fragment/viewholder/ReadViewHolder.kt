package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.ReadAdapter
import org.jxxy.debug.home.Fragment.item.Read
import org.jxxy.debug.home.databinding.StudentComponentReadBinding

class ReadViewHolder  (val binding: StudentComponentReadBinding) :
    MultipleViewHolder<Read>(binding.root) {
    private val adapter = ReadAdapter()

    init {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 2)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(12f, 14f, 2))
        binding.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: Read) {
        binding.read.text = entity.typeName
        entity.studyInfos?.let { List ->
            adapter.clear()
            val tools = Read(entity.type,entity.typeName, List)
            for (i in 1..80) {
                adapter.add(tools)
            }
        }
    }
}