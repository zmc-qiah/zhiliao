package org.jxxy.debug.society.holder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.AnswerBean
import org.jxxy.debug.society.bean.CreatorBean
import org.jxxy.debug.society.databinding.ItemAnswerAnswerBinding
import org.jxxy.debug.society.databinding.ItemCreatorBinding

class CreatorHolder (val binding: ItemCreatorBinding) : MultipleViewHolder<CreatorBean>(binding.root) {
    override fun setHolder(entity: CreatorBean) {
    binding.authoridTv.text=entity.authoridTv
        binding.titleTv.text=entity.titleTv
        binding.imageImg.setImageResource(entity.imageImg)
    }


}