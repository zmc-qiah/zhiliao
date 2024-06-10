package org.jxxy.debug.society.holder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.society.bean.LectureBean
import org.jxxy.debug.society.bean.PlayBean
import org.jxxy.debug.society.databinding.ItemLectureBinding
import org.jxxy.debug.society.databinding.ItemPlayBinding

class PlayHolder (view : ItemPlayBinding):
    SingleViewHolder<ItemPlayBinding, PlayBean>(view){
    override fun setHolder(entity: PlayBean) {
        view.activityImg.load(entity.activityImg,10)
        view.titleTv.text=entity.titleTv
        view.placeTv.text=entity.placeTv
        view.memberTv.text=entity.memberTv

    }
}