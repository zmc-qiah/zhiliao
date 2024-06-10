package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Breakthough
import org.jxxy.debug.home.databinding.ItemBreakthoughBinding

class BreakthoughItemViewHolder (view: ItemBreakthoughBinding) :
    SingleViewHolder<ItemBreakthoughBinding, Breakthough>(view) {
    override fun setHolder(entity: Breakthough) {
        val List = entity.thoughInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.breakthough1.load(tool.photo)
            view.breakthoughTv1.text = tool.title
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}