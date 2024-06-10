package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.News
import org.jxxy.debug.home.Fragment.item.Read
import org.jxxy.debug.home.databinding.ItemNewsBinding
import org.jxxy.debug.home.databinding.ItemReadBinding

class ReadItemViewHolder (view: ItemReadBinding) :
    SingleViewHolder<ItemReadBinding, Read>(view) {
    override fun setHolder(entity: Read) {
        val List = entity.studyInfos
        if (List != null && position < List.size) {
            val tool = List[position]
            view.readIv1.load(tool.photo)
            view.readTv1.text = tool.title
            view.iv1.text = tool.shortTitle
            view.root.singleClick {
                tool?.scheme?.navigation(context)
            }
        }
    }
}