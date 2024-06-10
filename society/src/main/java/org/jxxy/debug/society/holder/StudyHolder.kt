package org.jxxy.debug.society.holder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.society.bean.AibreakBean
import org.jxxy.debug.society.bean.StudyBean
import org.jxxy.debug.society.databinding.ItemAibreakBinding
import org.jxxy.debug.society.databinding.ItemStudyBinding

class StudyHolder  (view : ItemStudyBinding):
    SingleViewHolder<ItemStudyBinding, StudyBean >(view){
    override fun setHolder(entity: StudyBean) {
        view.lowTv.text=entity.lowTv

    }
}