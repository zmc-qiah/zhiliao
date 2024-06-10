package org.jxxy.debug.home.Fragment.viewholder

import CustomDividerItemDecoration
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.TedAdapter
import org.jxxy.debug.home.Fragment.item.Ted
import org.jxxy.debug.home.Fragment.item.bean.TedBean
import org.jxxy.debug.home.R
import org.jxxy.debug.home.databinding.StudyComponentTedBinding

class TedViewHolder (val binding: StudyComponentTedBinding,val scope: LifecycleCoroutineScope? =null) :
    MultipleViewHolder<Ted>(binding.root) {
    private val adapter = TedAdapter()
    private var list= listOf<TedBean>()
    init {
        binding.recyclerview.layoutManager = GridLayoutManager(itemView.context, 1)
        binding.recyclerview.addItemDecoration(SpanItemDecoration(7f, 15f, 1))

        val dividerDrawable = ContextCompat.getDrawable(itemView.context, R.drawable.divider_blue)!!
        val itemDecoration = CustomDividerItemDecoration(dividerDrawable)
        binding.recyclerview.addItemDecoration(itemDecoration)


        binding.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: Ted) {
        binding.ted.text = entity.typeName
        entity.tedInfos?.let { List ->
            list=List
            adapter.clear()
            val tools = Ted(entity.type,entity.typeName,List)
            for (i in 1..80) {
                adapter.add(tools)
            }
        }
        binding.TedViewSwitcher.initFactory(14f,false)
        scope?.let {
            binding.TedViewSwitcher.setData(list,
                it
            )
        }
    }
}