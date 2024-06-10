package org.jxxy.debug.society.holder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.society.bean.MyclassBean
import org.jxxy.debug.society.bean.StudylowBean
import org.jxxy.debug.society.databinding.ItemMyclassBinding
import org.jxxy.debug.society.databinding.ItemStudylowBinding

class MyclassHolder(val binding: ItemMyclassBinding) : MultipleViewHolder<MyclassBean>(binding.root) {
    override fun setHolder(entity: MyclassBean) {
        binding.imageImg.load(entity.image,10)
        binding.textTv.text=entity.textTv
        binding.progressTv.text=entity.progressTv
        itemView.singleClick {
            entity.scheme.navigation(context)
        }
    }

}