package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Book
import org.jxxy.debug.home.Fragment.item.Interview
import org.jxxy.debug.home.databinding.ItemInterviewBinding
import org.jxxy.debug.home.databinding.StudyComponentInterviewBinding
import org.jxxy.debug.home.databinding.StudyComponentRecommendBookBinding

class InterviewItemViewHolder (val binding: ItemInterviewBinding) :
    SingleViewHolder<ItemInterviewBinding,Interview>(binding) {
    override fun setHolder(entity: Interview) {
        binding.im.load(entity.videoPhoto)
        binding.tv1.text = entity.authorName
        binding.tv2.text = entity.authorJob
        binding.root.singleClick {
            entity.scheme?.navigation(context)
        }
    }
}