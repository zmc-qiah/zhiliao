package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Practice
import org.jxxy.debug.home.databinding.PracticeBinding

class PracticeViewHolder (val binding: PracticeBinding) :
    MultipleViewHolder<Practice>(binding.root) {
    override fun setHolder(entity: Practice) {
        binding.im2.load(entity.quickEntryInfos?.get(0)?.photoUrl)
        binding.im1.load(entity.quickEntryInfos?.get(1)?.photoUrl)
        binding.im3.load(entity.quickEntryInfos?.get(2)?.photoUrl)
        binding.tv2.text = entity.quickEntryInfos?.get(0)?.entryName
        binding.tv1.text = entity.quickEntryInfos?.get(1)?.entryName
        binding.tv3.text = entity.quickEntryInfos?.get(2)?.entryName
        binding.con8.singleClick {
            entity.quickEntryInfos?.get(0)?.scheme?.navigation(context)
        }
        binding.con7.singleClick {
            entity.quickEntryInfos?.get(1)?.scheme?.navigation(context)
        }
        binding.con3.singleClick {
            entity.quickEntryInfos?.get(2)?.scheme?.navigation(context)
        }
    }
}