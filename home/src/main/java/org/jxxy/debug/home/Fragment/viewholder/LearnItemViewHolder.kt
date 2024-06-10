package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.GridFour
import org.jxxy.debug.home.Fragment.item.Learn
import org.jxxy.debug.home.databinding.ItemLearnBinding
import org.jxxy.debug.home.databinding.ItemNewsBinding

class LearnItemViewHolder (view: ItemLearnBinding) :
    SingleViewHolder<ItemLearnBinding, Learn>(view) {
    override fun setHolder(entity: Learn) {
        val List = entity.studyInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.yuan1.load(tool.photo)
            view.tv1.text = tool.title
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}