package org.jxxy.debug.search.holder
import navigation
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.search.bean.SearchMiddleSubject
import org.jxxy.debug.search.databinding.ItemSearchMidldleBinding

class SearchMiddleLeftHolder (view :ItemSearchMidldleBinding):
    SingleViewHolder<ItemSearchMidldleBinding, SearchMiddleSubject>(view){
    override fun setHolder(entity: SearchMiddleSubject) {

        view.imageImg.load(entity.imageId,10)
        view.titleTv.text=entity.title
        view.textTv.text=entity.text
        view.tagTv.text=entity.tag
        itemView.singleClick {
            entity.scheme.navigation(context)
        }

    }
}