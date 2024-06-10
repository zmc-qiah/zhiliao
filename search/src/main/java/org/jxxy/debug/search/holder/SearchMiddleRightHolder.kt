package org.jxxy.debug.search.holder

import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick

import org.jxxy.debug.search.bean.SearchMiddleTopic
import org.jxxy.debug.search.databinding.ItemSearchMiddle2Binding


class SearchMiddleRightHolder (view : ItemSearchMiddle2Binding):
    SingleViewHolder<ItemSearchMiddle2Binding, SearchMiddleTopic>(view){
    override fun setHolder(entity: SearchMiddleTopic) {
        view.textTv.text=entity.text
        itemView.singleClick {
            entity.scheme.navigation(context)
        }

    }
}