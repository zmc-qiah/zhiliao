package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Tools
import org.jxxy.debug.home.Fragment.item.bean.ToolsBean
import org.jxxy.debug.home.databinding.ItemToolsBinding


class ToolsItemViewHolder (view: ItemToolsBinding) :
    SingleViewHolder<ItemToolsBinding, Tools>(view) {
    override fun setHolder(entity: Tools) {
        val toolList = entity.aiHouses
        if (toolList != null && position < toolList.size) {
            val tool = toolList[position]
            view.toolImg.load(tool.photo)
            view.toolTv.text = tool.name
            view.root.singleClick {
                tool.scheme?.navigation(context)
            }
        }
    }


}