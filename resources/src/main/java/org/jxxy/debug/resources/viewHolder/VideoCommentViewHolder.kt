package org.jxxy.debug.resources.viewHolder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.gone
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.common.R
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.resources.bean.Comment
import org.jxxy.debug.resources.databinding.ItemCommentBinding

class VideoCommentViewHolder(binding: ItemCommentBinding) : SingleViewHolder<ItemCommentBinding, Comment>(binding) {
    override fun setHolder(entity: Comment) {
        view.commenterImageView.load(entity.headPhoto, true)
        if (entity.commentPhoto != null && entity.commentPhoto != "") {
            view.commentImageView.load(entity.commentPhoto,6)
        } else {
            view.commentImageView.gone()
        }
        view.commenterTextView.text = entity.userName
        view.commentTextView.text = entity.comment
        view.likeTextView.text = entity.commentLikes.toString()
        view.createDateTV.text = entity.createTime
        view.likeIcon.setTag(view.likeIcon.id,false)
        view.likeIcon.singleClick {
            if (it.getTag(view.likeIcon.id) as Boolean){
                view.likeIcon.text = ResourceUtil.getString(R.string.like_icon)
                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.dark_grey))
                view.likeTextView.setTextColor(ResourceUtil.getColor(R.color.dark_grey))
                view.likeTextView.text = (view.likeTextView.text.toString().toInt() - 1).toString()
                view.likeIcon.setTag(view.likeIcon.id,false)
            }else{
                view.likeIcon.text = ResourceUtil.getString(R.string.like_fill)
                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.primary_100))
                view.likeTextView.setTextColor(ResourceUtil.getColor(R.color.primary_100))
                view.likeTextView.text = (view.likeTextView.text.toString().toInt() + 1).toString()
                view.likeIcon.setTag(view.likeIcon.id,true)
            }
        }
    }
}
