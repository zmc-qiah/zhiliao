package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Popularize
import org.jxxy.debug.home.databinding.ItemPopularizeBinding

class PopularizeItemViewHolder (view: ItemPopularizeBinding) :
    SingleViewHolder<ItemPopularizeBinding, Popularize>(view) {
    override fun setHolder(entity: Popularize) {
        val List = entity.quickEntryInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.im.load(tool.photoUrl)
            view.tv.text = tool.entryName
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}