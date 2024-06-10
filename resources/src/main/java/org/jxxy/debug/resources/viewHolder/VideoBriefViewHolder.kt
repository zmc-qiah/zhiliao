package org.jxxy.debug.resources.viewHolder

import android.util.Log
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.resources.bean.ResourceInfo
import org.jxxy.debug.resources.databinding.ItemVideoBriefBinding

class VideoBriefViewHolder(val binding: ItemVideoBriefBinding) : MultipleViewHolder2<ItemVideoBriefBinding, ResourceInfo>(binding) {
    override fun setHolder(entity: ResourceInfo) {
        entity.let {
            view.authorImageView.load(it.resourceAuthorHead, true)
            view.authorTextView.text = it.resourceAuthorName
            if(!it.resourceLabel.isNullOrEmpty()) {
                view.lableTextView.text = it.resourceLabel
            }
            view.readTV.text = it.resourceReads.toString()
            view.commentTV.text = it.resourceComments.toString()
            Log.d("TAG", "setHolder: " + entity)
            view.videoTitleTextView.text = it.resourceTitle
            view.createDateTV.text = it.createTime
        }
    }
}
