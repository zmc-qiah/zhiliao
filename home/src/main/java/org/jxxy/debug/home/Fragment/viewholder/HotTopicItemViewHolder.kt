package org.jxxy.debug.home.Fragment.http.viewholder

import android.util.Log
import androidx.lifecycle.Lifecycle
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Carousel
import org.jxxy.debug.home.Fragment.item.HotTopic
import org.jxxy.debug.home.Fragment.item.bean.HotTopicBean
import org.jxxy.debug.home.databinding.ComponentCarouselBinding
import org.jxxy.debug.home.databinding.ComponentHotTopicBinding
import org.jxxy.debug.home.databinding.ItemHotTopicBinding

class HotTopicItemViewHolder (view: ItemHotTopicBinding) :
    SingleViewHolder<ItemHotTopicBinding, HotTopic>(view){
    override fun setHolder(entity: HotTopic) {
        val List = entity.hotTopic
        if (List != null && position < List.size) {
            val tool = List[position]
            view.tvItemTotTopic1.text = tool.title
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}