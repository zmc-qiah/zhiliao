package org.jxxy.debug.home.Fragment.viewholder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.home.Fragment.item.Flash
import org.jxxy.debug.home.databinding.ComponentFlashBinding

class FlashViewHolder (val binding: ComponentFlashBinding) :
    MultipleViewHolder<Flash>(binding.root) {
    override fun setHolder(entity: Flash) {
        binding.flash.text = entity.typeName
        if (entity.flashInfos?.size?:0>1){
            binding.flashTv1.text = entity.flashInfos?.get(1)?.title
            binding.flash1.load(entity.flashInfos?.get(1)?.photo,20)
        }
    }


}