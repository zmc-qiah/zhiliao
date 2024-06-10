package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Advertise
import org.jxxy.debug.home.databinding.PracticeComponentAdvertiseBinding

class AdvertiseViewHolder (val binding: PracticeComponentAdvertiseBinding) :
    MultipleViewHolder<Advertise>(binding.root) {
    override fun setHolder(entity: Advertise) {
        binding.advertise.text = entity.typeName
        if(entity.activityInfos?.size?:0>2) {
            binding.tv1.text = entity.activityInfos?.get(0)?.title
            binding.tv2.text = entity.activityInfos?.get(1)?.title
            binding.tv3.text = entity.activityInfos?.get(2)?.title
            binding.iv1.load(entity.activityInfos?.get(0)?.photo)
            binding.iv2.load(entity.activityInfos?.get(1)?.photo)
            binding.iv3.load(entity.activityInfos?.get(2)?.photo)
            binding.iv1.singleClick {
                entity.activityInfos?.get(0)?.scheme?.navigation(context)
            }
            binding.iv2.singleClick {
                entity.activityInfos?.get(1)?.scheme?.navigation(context)
            }
            binding.iv3.singleClick {
                entity.activityInfos?.get(2)?.scheme?.navigation(context)
            }
        }
    }
}