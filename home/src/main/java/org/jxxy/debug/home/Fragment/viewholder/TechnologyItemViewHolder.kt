package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.News
import org.jxxy.debug.home.Fragment.item.Technology
import org.jxxy.debug.home.databinding.ItemNewsBinding
import org.jxxy.debug.home.databinding.ItemTechnologyBinding

class TechnologyItemViewHolder (view: ItemTechnologyBinding) :
    SingleViewHolder<ItemTechnologyBinding, Technology>(view) {
    override fun setHolder(entity: Technology) {
        val List = entity.technologyInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.technologyName1.text = tool.title
            view.technologyContext1.text = tool.content
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}