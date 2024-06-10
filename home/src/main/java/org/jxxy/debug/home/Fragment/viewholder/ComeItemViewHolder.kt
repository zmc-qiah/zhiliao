package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Come
import org.jxxy.debug.home.databinding.ItemComeBinding
import org.jxxy.debug.home.databinding.PracticeComponentComeBinding

class ComeItemViewHolder (val view: ItemComeBinding) :
    MultipleViewHolder<Come>(view.root) {
    override fun setHolder(entity: Come) {
        val List = entity.joinInfo
        if (List != null && position < List.size) {
            val tool = List[position]
            view.iv1.load(tool.photoUrl)
            view.tv.text = tool.title
        }
    }
}