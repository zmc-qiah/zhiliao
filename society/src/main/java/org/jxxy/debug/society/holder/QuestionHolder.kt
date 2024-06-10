package org.jxxy.debug.society.holder
import org.jxxy.debug.society.bean.QuestionBean
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.databinding.ItemAnswerQuestionBinding
class QuestionHolder (val binding: ItemAnswerQuestionBinding) : MultipleViewHolder<QuestionBean>(binding.root) {
    override fun setHolder(entity: QuestionBean) {

        binding.authoridTv.text=entity.authoridTv
        binding.titleTv.text=entity.titleTv
        binding.tagTv.text=entity.tagTv
        binding.questionTv.text=entity.questionTv
        binding.authorImg.load (entity.authorImg,100)


    }

}