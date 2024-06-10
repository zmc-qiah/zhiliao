package org.jxxy.debug.classification.viewHolder

import android.util.Log
import androidx.fragment.app.FragmentManager
import navigation
import org.jxxy.debug.classification.databinding.ItemClassificationResourceTextBinding
import org.jxxy.debug.common.R
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick

class ClassificationResourceTextViewHolder(val view: ItemClassificationResourceTextBinding) : MultipleViewHolder<Resource>(view.root) {
    var fragment: FragmentManager? = null
    val TAG = R.id.likeIcon
    override fun setHolder(entity: Resource) {
        if(entity.resourceAuthorName?.length?:0 <= 3) {
            view.authorNameTV.text = entity.resourceAuthorName
        }else{
            view.authorNameTV.text = entity.resourceAuthorName?.substring(0,3) + ".."
        }
        if(entity.resourceTitle?.length?:0 >0){
            view.tileTV.text = entity.resourceTitle
        }else{
            view.tileTV.text = entity.resourceTitle?.substring(0,20) + "..."
        }
        view.likeTV.text = entity.resourceLikes.toString()
        view.readTV.text = entity.resourceReads.toString()
        view.auImageView.load(entity.resourceAuthorHead, true)
        if(!entity.resourcePhoto.isNullOrEmpty()) {
            view.thumbnailIV.loads(entity.resourcePhoto, 8, context)
        }
        if (entity.resourceType == 4){
            view.aTv.text = "漫画"
        }else{
            view.aTv.text = "文本"
        }
        Log.d("ResourcePhoto","${entity.resourcePhoto}")
        view.likeIcon.setTag(TAG,false)
        entity.createTime?.let {
            view.createDateTV.text = it.split(" ")[0].substring(2)
        }
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
                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.primary_100))
                view.likeTV.text = (view.likeTV.text.toString().toInt() + 1).toString()
                view.likeIcon.setTag(TAG,true)
            }
        }
        view.auImageView.singleClick { image ->
            fragment?.let {
                ImageClickDialog(image).show(it)
            }
        }
        view.thumbnailIV.singleClick { image ->
            fragment?.let {
                ImageClickDialog(image).show(it)
            }
        }
        if(entity.scheme == null) {
            view.root.singleClick {
                entity.resourceId?.let { it1 -> goResource(context, it1.toInt()) }
            }
        }else{
            view.root.singleClick {
                entity.scheme?.navigation(context)
            }
        }
    }
}
