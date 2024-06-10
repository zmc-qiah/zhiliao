package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.common.service.TestServiceCommon
import org.jxxy.debug.common.service.goHome
import org.jxxy.debug.corekit.common.CommonServiceManager
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.member.bean.PointTask
import org.jxxy.debug.member.databinding.ItemPointTaskBinding

class PointTaskViewHolder(binding: ItemPointTaskBinding) : SingleViewHolder<ItemPointTaskBinding, PointTask>(binding) {
    override fun setHolder(entity: PointTask) {
        view.pointPB.max = entity.maxPoint?.toInt()?:0
        view.pointPB.progress = entity.currentPoint?.toInt()?:0
        view.pointTaskButton.text = (entity.currentPoint.toString() + "/" + entity.maxPoint.toString()) ?: ""
        if ((entity.currentPoint == entity.maxPoint)) {
            view.pointTaskButton.isEnabled = false
            view.pointTaskButtonDescribe.hide()
        }
        view.pointTaskName.text = entity.name ?: ""
        view.pointTaskDescribe.text = entity.describe ?: ""
        view.pointTaskButton.singleClick {
            entity.name?.let {
                if (it.contains("文章")){
                    goHome(context)
                }else if (it.contains("视频")){
                    goHome(context)
                }else if (it.contains("问题")){
                    CommonServiceManager.service<TestServiceCommon>()?.goDailyAnswer(context)
                }
            }
        }
    }
}
