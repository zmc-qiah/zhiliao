package org.jxxy.debug.society.holder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.society.bean.RecommendclassBean
import org.jxxy.debug.society.databinding.ItemRecommendclassBinding
import org.jxxy.debug.society.databinding.ItemStudylowBinding

class RecommendclassHolder (val binding: ItemRecommendclassBinding ) : MultipleViewHolder<RecommendclassBean>(binding.root) {
    override fun setHolder(entity: RecommendclassBean) {
       binding.imageImg.load(entity.imageID,20)
        binding.textTv.text=entity.text
        binding.progressTv.text=entity.progressTv
        itemView.singleClick {
            entity.scheme?.navigation(context)
        }


    }
}