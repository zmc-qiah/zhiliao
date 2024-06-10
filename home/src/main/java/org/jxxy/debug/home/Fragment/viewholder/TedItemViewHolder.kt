package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Ted
import org.jxxy.debug.home.databinding.ItemTedBinding

class TedItemViewHolder (view: ItemTedBinding) :
    SingleViewHolder<ItemTedBinding, Ted>(view) {
    override fun setHolder(entity: Ted) {
        view.tedNumber3.gone()
        val List = entity.tedInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.vd3.load(tool.photo,10)
            view.tedName3.text = tool.title
            view.tv.text = tool.type
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}