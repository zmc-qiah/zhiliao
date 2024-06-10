package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.News
import org.jxxy.debug.home.Fragment.item.Read
import org.jxxy.debug.home.Fragment.viewholder.NewsItemViewHolder
import org.jxxy.debug.home.Fragment.viewholder.ReadItemViewHolder
import org.jxxy.debug.home.databinding.ItemNewsBinding
import org.jxxy.debug.home.databinding.ItemReadBinding

class ReadAdapter : SingleTypeAdapter<Read>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return ReadItemViewHolder(
            ItemReadBinding.inflate(
                inflater,
                parent,
                false
            )
        ) as? SingleViewHolder<ViewBinding, Any>
    }
    override fun getItemCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
