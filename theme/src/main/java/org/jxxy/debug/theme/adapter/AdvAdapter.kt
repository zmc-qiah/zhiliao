package org.jxxy.debug.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.theme.bean.AiAdvBean
import org.jxxy.debug.theme.databinding.ItemAdvItemBinding
import org.jxxy.debug.theme.viewHolder.AiAdvBeanViewHolder

class AdvAdapter:SingleTypeAdapter<AiAdvBean>() {
    val list:List<AiAdvBean>
        get() {
            return data
        }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = AiAdvBeanViewHolder(
        ItemAdvItemBinding.inflate(inflater, parent, false)
    )
}