package org.jxxy.debug.resources.viewHolder

import androidx.fragment.app.Fragment
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jxxy.debug.common.http.repository.ResourceRepository
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.resources.R
import org.jxxy.debug.resources.bean.VideoIcon
import org.jxxy.debug.resources.databinding.ItemVideoIconBinding
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.util.goLogin
import org.jxxy.debug.resources.util.isLogin

class VideoIconViewHolder(binding: ItemVideoIconBinding, val viewModel: ResourceViewModel, val fragment: Fragment) : MultipleViewHolder2<ItemVideoIconBinding, VideoIcon>(binding) {
    override fun setHolder(entity: VideoIcon) {
        view.likeText.text = entity.likes.toString()
        view.shareText.text = "分享"
        view.unLikeText.text = "不喜欢"
        view.markText.text = "收藏"
        if (entity.myState.likeState == 1) {
            view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
            view.likeIcon.setTag(view.likeIcon.id,true)
        }else{
            view.likeIcon.setTag(view.likeIcon.id,false)
        }
        view.likeIcon.singleClick {icon->
            if ( icon.getTag(icon.id) as Boolean){
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        entity.id?.let {it1 -> ResourceRepository.cancelLike(it1.toInt())}
                    }?.let {
                        withContext(Dispatchers.Main){
                            if (it.code==0){
                                view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_icon)
                                view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                                view.likeText.text = it.data.toString()
                                view.likeIcon.setTag(icon.id,false)
                            }
                        }
                    }
                }
            }
            else {
                GlobalScope.launch {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            entity.id?.let {it1 -> ResourceRepository.addLike(it1.toInt())}
                        }?.let {
                            withContext(Dispatchers.Main){
                                if (it.code==0){
                                    view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                                    view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                                    view.likeText.text = it.data.toString()
                                    view.likeIcon.setTag(icon.id,true)
                                }
                            }
                        }
                    }
                }
            }
        }
        if (entity.myState.collectState == 1) {
            view.markIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
            view.markIcon.setTag(view.markIcon.id,true)
        }else{
            view.markIcon.setTag(view.markIcon.id,false)
        }
        view.markIcon.singleClick(increase = true) { icon->
            if (!isLogin()) {
                goLogin(context)
                return@singleClick
            }
            if ( icon.getTag(icon.id) as Boolean){
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        entity.id?.let {it1 -> ResourceRepository.cancelCollection(it1.toInt())}
                    }?.let {
                        withContext(Dispatchers.Main){
                            if (it.code==0){
                                view.markIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.icon_mark_Hollow)
                                view.markIcon.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                            }
                            view.markIcon.setTag(icon.id,false)
                        }
                    }
                }
            }
            else {
                GlobalScope.launch {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            entity.id?.let {it1 -> ResourceRepository.addCollection(it1.toInt())}
                        }?.let {
                            withContext(Dispatchers.Main){
                                if (it.code==0){
                                    view.markIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.mark)
                                    view.markIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                                }
                                view.markIcon.setTag(icon.id,true)
                            }
                        }
                    }
                }
            }
        }
        view.unLikeIcon.setTag(view.unLikeIcon.id,false)
        view.unLikeIcon.singleClick(increase = true) {icon->
                if (icon.getTag(view.unLikeIcon.id) as Boolean){
                    if (view.likeIcon.getTag(view.likeIcon.id) as Boolean){
                        GlobalScope.launch {
                            withContext(Dispatchers.IO){
                                entity.id?.let {it1 -> ResourceRepository.cancelLike(it1.toInt())}
                            }?.let {
                                withContext(Dispatchers.Main){
                                    if (it.code==0){
                                        view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                                        view.likeIcon.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                                        view.likeText.text = it.data.toString()
                                        view.likeIcon.setTag(view.likeIcon .id,false)
                                    }
                                }
                            }
                        }
                    }
                    view.unLikeIcon.setTextColor(ResourceUtil.getColor(R.color.grey_dark))
                    view.unLikeIcon.setTag(view.unLikeIcon.id,false)
                }else{
                    view.unLikeIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                    view.unLikeIcon.setTag(view.unLikeIcon.id,true)
                }
        }
        viewModel.likeLiveData.observe(fragment) {
            it.onSuccess {
                view.likeText.text = (it ?: 0).toString()
                if (entity.myState.likeState == 1) {
                    entity.myState.likeState = 0
                } else {
                    entity.myState.likeState = 1
                }
            }
        }
        viewModel.addMarkLiveData.observe(fragment) {
            it.onSuccess {
                view.markIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                entity.myState.collectState = 1
            }
        }
        viewModel.cancelMarkLiveData.observe(fragment) {
            it.onSuccess {
                view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.grey))
                entity.myState.collectState = 0
            }
        }
    }
}
