package org.jxxy.debug.society.holder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.LectureBean
import org.jxxy.debug.society.bean.LectureTimeBean
import org.jxxy.debug.society.databinding.ItemLectureBinding
import org.jxxy.debug.society.databinding.ItemLecturetimeBinding

class LectureTimeHolder (view : ItemLecturetimeBinding):
    SingleViewHolder<ItemLecturetimeBinding, LectureTimeBean>(view){
    override fun setHolder(entity: LectureTimeBean) {
      view.dateTv.text=entity.date
        view.weekTv.text=entity.week
    }
}