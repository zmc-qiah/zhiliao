package org.jxxy.debug.classification.viewHolder

import android.util.Log
import androidx.fragment.app.FragmentManager
import navigation
import org.jxxy.debug.classification.databinding.ItemClassificationResourceVideoBinding
import org.jxxy.debug.common.R
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick

class ClassificationResourcesVideoViewHolder(val view: ItemClassificationResourceVideoBinding) : MultipleViewHolder<Resource>(view.root) {
    var fragment: FragmentManager ? = null
    val TAG = R.id.likeIcon
    override fun setHolder(entity: Resource) {
        if(!entity.resourcePhoto.isNullOrEmpty()) {
            view.videoPhotoIV.loads(entity.resourcePhoto, 8, context)
        }
        Log.d("ResourcePhoto","${entity.resourcePhoto}")
        view.authorImageView.load(entity.resourceAuthorHead, true)
        if(entity.resourceAuthorName?.length!! <= 3) {
            view.authorNameTV.text = entity.resourceAuthorName
        }else{
            view.authorNameTV.text = entity.resourceAuthorName?.substring(0,3) + ".."
        }
        if(entity.resourceTitle?.length!! <= 15){
            view.videoTileTV.text = entity.resourceTitle
        }else{
            view.videoTileTV.text = entity.resourceTitle?.substring(0,15) + "..."
        }
        view.likeTV.text = entity.resourceLikes.toString()
        view.readTV.text = entity.resourceReads.toString()
        entity.createTime?.let {
            view.createDateTV.text = it.split(" ")[0].substring(2)
        }
        view.authorImageView.singleClick { image ->
            fragment?.let {
                ImageClickDialog(image).show(it)
            }
        }
        if(entity.scheme == null) {
            entity.resourceId?.let { it1 ->
                view.root.singleClick {
                    goResource(context, it1.toInt())
                }
            }
        }else{
            view.root.singleClick {
                entity.scheme?.navigation(context)
            }
        }
        view.likeIcon.setTag(TAG,false)
        if (!(view.likeIcon.getTag(TAG) as Boolean)){
            view.likeIcon.text = ResourceUtil.getString(R.string.like_icon)
            view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.primary_100))
            view.likeIcon.setTag(TAG,false)
        }else{
            view.likeIcon.text = ResourceUtil.getString(R.string.like_fill)
            view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.primary_100))
            view.likeTV.text = (view.likeTV.text.toString().toInt() + 1).toString()
            view.likeIcon.setTag(TAG,true)
        }
        view.likeIcon.singleClick {
            if ( it.getTag(TAG) as Boolean){
                view.likeIcon.text = ResourceUtil.getString(R.string.like_icon)
                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.primary_100))
                view.likeTV.text = (view.likeTV.text.toString().toInt() - 1).toString()
                view.likeIcon.setTag(TAG,false)
            }else{
                view.likeIcon.text = ResourceUtil.getString(R.string.like_fill)
                view.likeTV.text = (view.likeTV.text.toString().toInt() + 1).toString()
                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.primary_100))
                view.likeIcon.setTag(TAG,true)
            }
        }
    }
}
