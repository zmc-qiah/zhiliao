package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.bean.TabInfo.Identity
import org.jxxy.debug.home.databinding.FragmentHomeBinding

class IdentityViewHolder  (val binding: FragmentHomeBinding) :
    SingleViewHolder<FragmentHomeBinding, Identity>(binding) {
    override fun setHolder(entity: Identity) {
    }
}