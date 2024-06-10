package org.jxxy.debug.home.Fragment.viewholder

import androidx.recyclerview.widget.GridLayoutManager
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.home.Fragment.adapter.ToolsAdapter
import org.jxxy.debug.home.Fragment.fragment.StudyFragment
import org.jxxy.debug.home.Fragment.item.Tools
import org.jxxy.debug.home.databinding.StudyComponentToolsBinding

class ToolsViewHolder(val view: StudyComponentToolsBinding, val fragment: StudyFragment? = null,) :
    MultipleViewHolder<Tools>(view.root) {
    private val adapter = ToolsAdapter()

    init {
        view.recyclerview.layoutManager = GridLayoutManager(itemView.context, 4)
        view.recyclerview.addItemDecoration(SpanItemDecoration(10f, 0f, 4))
        view.recyclerview.adapter = adapter
    }

    override fun setHolder(entity: Tools) {
        entity.aiHouses?.let { toolsList ->
            adapter.clear()
            val tools = Tools(entity.type,entity.typeName, toolsList)
            for (i in 1..80) { // 将 n 替换为最大数据项数量这样就可以实现数据库有几个数据首页就展示多少个了
                adapter.add(tools)
            }
        }
    }
}
