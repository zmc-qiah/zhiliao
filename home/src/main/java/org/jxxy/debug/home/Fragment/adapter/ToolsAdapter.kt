package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.Tools
import org.jxxy.debug.home.Fragment.item.bean.ToolsBean
import org.jxxy.debug.home.Fragment.viewholder.ToolsItemViewHolder
import org.jxxy.debug.home.databinding.ItemToolsBinding

class ToolsAdapter : SingleTypeAdapter<Tools>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return ToolsItemViewHolder(
            ItemToolsBinding.inflate(
                inflater,
                parent,
                false
            )
        ) as? SingleViewHolder<ViewBinding, Any>
    }
    override fun getItemCount(): Int {
        return 8
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
