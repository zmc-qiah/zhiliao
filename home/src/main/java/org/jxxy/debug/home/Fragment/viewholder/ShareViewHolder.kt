package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.home.Fragment.item.Share
import org.jxxy.debug.home.databinding.PracticeComponentShareBinding

class ShareViewHolder (val binding: PracticeComponentShareBinding) :
    MultipleViewHolder<Share>(binding.root) {
    override fun setHolder(entity: Share) {
        binding.shareTitle.text = entity.typeName
        binding.tv1.text = entity.content
        binding.im.load(entity.photoUrl)
    }
}