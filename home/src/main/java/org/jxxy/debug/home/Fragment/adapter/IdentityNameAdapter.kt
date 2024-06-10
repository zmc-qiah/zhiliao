package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.bean.TabInfo.Identity
import org.jxxy.debug.home.Fragment.viewholder.IdentityViewHolder
import org.jxxy.debug.home.databinding.FragmentHomeBinding

class IdentityNameAdapter : SingleTypeAdapter<Identity>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return IdentityViewHolder(
            FragmentHomeBinding.inflate(
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
