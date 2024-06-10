package org.jxxy.debug.society.holder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.society.bean.ExcellebtClassBean
import org.jxxy.debug.society.bean.RecommendclassBean
import org.jxxy.debug.society.databinding.ItemClassBinding
import org.jxxy.debug.society.databinding.ItemRecommendclassBinding

class ExcellentclassHolder (val binding: ItemClassBinding) : MultipleViewHolder<ExcellebtClassBean>(binding.root) {
    override fun setHolder(entity: ExcellebtClassBean) {
        binding.classImg.load(entity.image,40)
        binding.startlearnTv.text=entity.text
        itemView.singleClick {
            entity.scheme?.navigation(context)
        }
    }

}