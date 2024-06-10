package org.jxxy.debug.search.holder

import android.os.Bundle
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.search.activity.SearchEndActivity
import org.jxxy.debug.search.bean.SearchHistory
import org.jxxy.debug.search.databinding.ItemSearchHistoryBinding

class SearchMiddleHistoryHolder(view: ItemSearchHistoryBinding) :
    SingleViewHolder<ItemSearchHistoryBinding, SearchHistory>(view) {
    override fun setHolder(entity: SearchHistory) {
        view.historyTv.text = entity.text
        itemView.singleClick {
            val bundle = Bundle()
            bundle.putString("keyword", entity.text)
            context.startActivity<SearchEndActivity>(bundle)
        }
    }
}