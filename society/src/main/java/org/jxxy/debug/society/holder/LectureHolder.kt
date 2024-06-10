package org.jxxy.debug.society.holder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.LectureBean
import org.jxxy.debug.society.databinding.ItemLectureBinding

class LectureHolder (view : ItemLectureBinding ):
    SingleViewHolder<ItemLectureBinding, LectureBean >(view){
    override fun setHolder(entity: LectureBean) {
        view.lectureImg.load(entity.lectureImg,10)
        view.titleTv.text=entity.titleTv
        view.placeTv.text=entity.placeTv
        view.memberTv.text=entity.memberTv

    }
}