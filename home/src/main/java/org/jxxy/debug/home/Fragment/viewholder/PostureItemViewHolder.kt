package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.News
import org.jxxy.debug.home.Fragment.item.Posture
import org.jxxy.debug.home.databinding.ItemNewsBinding

class PostureItemViewHolder (view: ItemNewsBinding) :
    SingleViewHolder<ItemNewsBinding, Posture>(view) {
    override fun setHolder(entity: Posture) {
        val List = entity.charmInfoList
        if (List != null && position < List.size) {
            val tool = List[position]
            view.news1.load(tool.photo)
            view.newsIntro1.text = tool.title
        }
    }
}