package org.jxxy.debug.common.viewHolder

import androidx.fragment.app.FragmentManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import navigation
import org.jxxy.debug.common.R
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.common.databinding.ItemResourcesVideoBinding
import org.jxxy.debug.common.dialog.ImageClickDialog
import org.jxxy.debug.common.http.repository.ResourceRepository
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.singleClick

class ResourcesVideoViewHolder(val view: ItemResourcesVideoBinding) :
    MultipleViewHolder<Resource>(view.root) {
    var fragment: FragmentManager? = null
    val TAG = R.id.likeIcon
    override fun setHolder(entity: Resource) {
        view.videoPhotoIV.load(
            entity.resourcePhoto
                ?: "https://i0.hdslb.com/bfs/archive/0e291149963b333021360535295f1e51db41c425.jpg@672w_378h_1c_!web-search-common-cover.webp"
        )
        view.authorImageView.load(entity.resourceAuthorHead, true)
        entity.resourceAuthorName.nullOrNot(
            {
                view.authorNameTV.text = "呜呜"
            },
            {
                view.authorNameTV.text = entity.resourceAuthorName
            }
        )
        view.videoTileTV.text = entity.resourceTitle
        view.likeTV.text = entity.resourceLikes.toString()
        view.readTV.text = (Math.random() * 100 + Math.random() * 10).toInt().toString()
        entity.createTime?.let {
            view.createDateTV.text = it.split(" ")[0]
        }
        view.authorImageView.singleClick { image ->
            fragment?.let {
                ImageClickDialog(image).show(it)
            }
        }
        view.root.singleClick {
            entity.scheme?.navigation(context)
        }
        view.likeIcon.setTag(TAG, false)
        view.likeIcon.singleClick {
            if (it.getTag(TAG) as Boolean) {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        entity.resourceId?.let { it1 -> ResourceRepository.cancelLike(it1.toInt()) }
                    }?.let {
                        withContext(Dispatchers.Main) {
                            if (it.code == 0) {
                                view.likeIcon.text = ResourceUtil.getString(R.string.like_icon)
                                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.bg_300))
                                view.likeTV.text = it.data.toString()
                            }
                            view.likeIcon.setTag(TAG, false)
                        }
                    }
                }
            } else {
                GlobalScope.launch {
                    withContext(Dispatchers.IO) {
                        entity.resourceId?.let { it1 -> ResourceRepository.addLike(it1.toInt()) }
                    }?.let {
                        withContext(Dispatchers.Main) {
                            if (it.code == 0) {
                                view.likeIcon.text = ResourceUtil.getString(R.string.like_fill)
                                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.primary_100))
                                view.likeTV.text = it.data.toString()
                            }
                            view.likeIcon.setTag(TAG, true)
                        }
                    }
                }
            }
        }
    }
}
