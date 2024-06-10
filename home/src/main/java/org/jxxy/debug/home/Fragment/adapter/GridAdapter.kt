package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.Grid
import org.jxxy.debug.home.Fragment.item.GridFour
import org.jxxy.debug.home.Fragment.viewholder.GridFourItemViewHolder
import org.jxxy.debug.home.Fragment.viewholder.GridItemViewHolder
import org.jxxy.debug.home.databinding.ItemGridBinding
import org.jxxy.debug.home.databinding.ItemNewsBinding

class GridAdapter : SingleTypeAdapter<Grid>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return GridItemViewHolder(
            ItemGridBinding.inflate(
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
