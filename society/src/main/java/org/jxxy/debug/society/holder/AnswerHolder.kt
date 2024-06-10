package org.jxxy.debug.society.holder

import org.jxxy.debug.society.bean.AnswerBean
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.databinding.ItemAnswerAnswerBinding

class AnswerHolder(val binding: ItemAnswerAnswerBinding) : MultipleViewHolder<AnswerBean>(binding.root) {
    override fun setHolder(entity: AnswerBean) {
        binding.answerTv.text =entity.text
        binding.imageImg2222.load(entity.image,100)
        binding.answernameTv.text=entity.answernameTv

    }


}
