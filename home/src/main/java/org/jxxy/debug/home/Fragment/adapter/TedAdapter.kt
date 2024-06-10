package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.Technology
import org.jxxy.debug.home.Fragment.item.Ted
import org.jxxy.debug.home.Fragment.viewholder.TechnologyItemViewHolder
import org.jxxy.debug.home.Fragment.viewholder.TedItemViewHolder
import org.jxxy.debug.home.databinding.ItemTechnologyBinding
import org.jxxy.debug.home.databinding.ItemTedBinding

class TedAdapter : SingleTypeAdapter<Ted>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return TedItemViewHolder(
            ItemTedBinding.inflate(
                inflater,
                parent,
                false
            )
        ) as? SingleViewHolder<ViewBinding, Any>
    }
    override fun getItemCount(): Int {
        return 3
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
