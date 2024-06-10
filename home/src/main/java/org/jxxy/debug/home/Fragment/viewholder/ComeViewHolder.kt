package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.home.Fragment.item.Come
import org.jxxy.debug.home.databinding.PracticeComponentComeBinding

class ComeViewHolder (val binding: PracticeComponentComeBinding) :
    MultipleViewHolder<Come>(binding.root) {
    override fun setHolder(entity: Come) {
        binding.title.text = entity.typeName
        if(entity.joinInfo?.size?:0>1) {
            binding.iv1.load(entity.joinInfo?.get(0)?.photoUrl)
            binding.iv2.load(entity.joinInfo?.get(1)?.photoUrl)
        }
    }
}