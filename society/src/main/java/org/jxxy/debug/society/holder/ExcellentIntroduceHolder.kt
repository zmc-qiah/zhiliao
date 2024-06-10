package org.jxxy.debug.society.holder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.society.bean.ExcellebtClassBean
import org.jxxy.debug.society.bean.ExcellentIntroduceBean
import org.jxxy.debug.society.databinding.ItemClassBinding
import org.jxxy.debug.society.databinding.ItemExcellentintroduceBinding

class ExcellentIntroduceHolder(val binding: ItemExcellentintroduceBinding) : MultipleViewHolder<ExcellentIntroduceBean>(binding.root) {
    override fun setHolder(entity: ExcellentIntroduceBean) {
       binding.introduceTv.text=entity.text
    }

}