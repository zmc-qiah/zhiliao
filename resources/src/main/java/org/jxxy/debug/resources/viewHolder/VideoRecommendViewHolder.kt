package org.jxxy.debug.resources.viewHolder

import android.view.ViewGroup
import navigation
import org.jxxy.debug.common.databinding.ItemResourceTextBinding
import org.jxxy.debug.common.util.addOne
import org.jxxy.debug.common.util.minusOne
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.resources.bean.RecommendInfo

class VideoRecommendViewHolder(binding: ItemResourceTextBinding) : MultipleViewHolder2<ItemResourceTextBinding, RecommendInfo>(binding) {
    override fun setHolder(entity: RecommendInfo) {
        val params = view.root.layoutParams as ViewGroup.MarginLayoutParams
        params.leftMargin = 12
        params.rightMargin = 12
        view.authorNameTV.text = entity.author
        view.tileTV.text = entity.title
        view.likeTV.text = entity.likes.toString()
        view.readTV.text = entity.readHot.toString()
        view.createDateTV.hide()
        view.auImageView.load(entity.authorHead, true)
        view.thumbnailIV.load(entity.photo,6)
        view.root.singleClick {
        entity.scheme?.navigation(context)
        view.likeIcon.tag = false
        view.likeIcon.singleClick {
            if (it.tag as Boolean){
                view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_icon)
                view.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.like_not_select))
                view.likeTV.minusOne()
            }else{
                view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                view.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.like_select))
                view.likeTV.addOne()
            }
            it.tag = !(it.tag as Boolean)
        }
        }
    }
}
