package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.News
import org.jxxy.debug.home.databinding.ItemNewsBinding

class NewsItemViewHolder (view: ItemNewsBinding) :
    SingleViewHolder<ItemNewsBinding, News>(view) {
    override fun setHolder(entity: News) {
        val List = entity.newInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.news1.load(tool.photo,16)
            view.newsIntro1.text = tool.title
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}