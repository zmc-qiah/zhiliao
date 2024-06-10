package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.Book
import org.jxxy.debug.home.Fragment.item.Come
import org.jxxy.debug.home.Fragment.viewholder.BookItemViewHolder
import org.jxxy.debug.home.Fragment.viewholder.ComeItemViewHolder
import org.jxxy.debug.home.databinding.ItemBooksBinding
import org.jxxy.debug.home.databinding.ItemComeBinding

class ComeAdapter : SingleTypeAdapter<Come>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return ComeItemViewHolder(
            ItemComeBinding.inflate(
                inflater,
                parent,
                false
            )
        ) as? SingleViewHolder<ViewBinding, Any>
    }
    override fun getItemCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
