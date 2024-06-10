package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Expert
import org.jxxy.debug.home.databinding.ComponentExpertBinding

class ExpertViewHolder (val binding: ComponentExpertBinding) :
  MultipleViewHolder<Expert>(binding.root) {
  override fun setHolder(entity: Expert) {

    binding.expertTvName1.text = entity.experts?.get(0)?.name
    binding.expertTitle.text = entity.typeName
    binding.expertTvContent1.text = entity.experts?.get(0)?.describe
    binding.expertTvContent2.text=entity.experts?.get(0)?.introduction
    binding.expertIv1.load(entity.experts?.get(0)?.photo,20)
    binding.expertIv1.singleClick {
      entity.experts?.get(0)?.scheme?.navigation(context)
    }


  }


}