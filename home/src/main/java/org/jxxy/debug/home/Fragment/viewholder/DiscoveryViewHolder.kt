package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Discovery
import org.jxxy.debug.home.databinding.PracticeComponentDiscoveryBinding

class DiscoveryViewHolder (val binding: PracticeComponentDiscoveryBinding) :
    MultipleViewHolder<Discovery>(binding.root) {
    override fun setHolder(entity: Discovery) {
        binding.discovery.text = entity.typeName
        if(entity.videoInfos?.size?:0>4) {
            binding.discoveryTitle1.text = entity.videoInfos?.get(0)?.videoTitle
            binding.tv1.text = entity.videoInfos?.get(1)?.videoTitle
            binding.tv2.text = entity.videoInfos?.get(2)?.videoTitle
            binding.tv3.text = entity.videoInfos?.get(3)?.videoTitle
            binding.tv4.text = entity.videoInfos?.get(4)?.videoTitle
            binding.discovery1.load(entity.videoInfos?.get(0)?.videoPhoto)
            binding.discoveryTitle2.load(entity.videoInfos?.get(1)?.videoPhoto)
            binding.discoveryTitle3.load(entity.videoInfos?.get(2)?.videoPhoto)
            binding.discoveryTitle4.load(entity.videoInfos?.get(3)?.videoPhoto)
            binding.discoveryTitle5.load(entity.videoInfos?.get(4)?.videoPhoto)
            binding.discovery1.singleClick {
                entity.videoInfos?.get(0)?.scheme?.navigation(context)
            }
            binding.discoveryTitle2.singleClick {
                entity.videoInfos?.get(1)?.scheme?.navigation(context)
            }
            binding.discoveryTitle3.singleClick {
                entity.videoInfos?.get(2)?.scheme?.navigation(context)
            }
            binding.discoveryTitle4.singleClick {
                entity.videoInfos?.get(3)?.scheme?.navigation(context)
            }
            binding.discoveryTitle5.singleClick {
                entity.videoInfos?.get(4)?.scheme?.navigation(context)
            }
        }
    }
}