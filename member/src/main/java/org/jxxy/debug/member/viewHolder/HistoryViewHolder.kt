package org.jxxy.debug.member.viewHolder

import android.util.Log
import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.bean.PastRecords
import org.jxxy.debug.member.databinding.ItemHistoryBinding

class HistoryViewHolder(binding: ItemHistoryBinding) : SingleViewHolder<ItemHistoryBinding, PastRecords>(binding) {
    override fun setHolder(entity: PastRecords) {
        Log.d("subscriaabeUi", "setHolder: " + entity.resourceTitle)
        view.historyContentTV.text = entity.resourceTitle
        view.historyTimeTV.text = entity.createTime
        entity.resourceId?.let { it1 ->
            view.root.singleClick { goResource(context, it1.toInt()) }
        }
    }
}
