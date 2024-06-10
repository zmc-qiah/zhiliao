package org.jxxy.debug.search.holder

import android.os.Bundle
import androidx.core.content.ContextCompat.startActivity
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.startActivity
import org.jxxy.debug.search.activity.SearchEndActivity
import org.jxxy.debug.search.bean.SearchRv
import org.jxxy.debug.search.databinding.ItemSearchrvBinding

class SearchRvHolder(view : ItemSearchrvBinding):
    SingleViewHolder<ItemSearchrvBinding, SearchRv >(view){
    override fun setHolder(entity: SearchRv) {
       view.textTv.text=entity.text
        itemView.singleClick {
            val bundle = Bundle()
            bundle.putString("keyword", entity.text)

            context.startActivity<SearchEndActivity>(bundle)
        }
    }
}