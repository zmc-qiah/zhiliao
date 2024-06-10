package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.member.R
import org.jxxy.debug.member.bean.GroupRank
import org.jxxy.debug.member.databinding.ItemGroupBankBinding

class GroupRankViewHolder(binding: ItemGroupBankBinding) : SingleViewHolder<ItemGroupBankBinding, GroupRank>(binding) {
    override fun setHolder(entity: GroupRank) {
        view.postionTextView.text = entity.position.toString()
        when (entity.position.toInt()){
            1->view.bankIcon.setTextColor(ResourceUtil.getColor(R.color.gold))
            2->view.bankIcon.setTextColor(ResourceUtil.getColor(R.color.silver))
            3->view.bankIcon.setTextColor(ResourceUtil.getColor(R.color.bronze))
            else->view.bankIcon.hide()
        }
        view.userPhotoIV.load(entity.userHead,true)
        view.userNameTV.text = entity.userName
        view.userPointTV.text = entity.score.toString()
    }
}
