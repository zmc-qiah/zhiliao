package org.jxxy.debug.resources.viewHolder

import android.app.Activity
import android.util.Log
import android.view.ViewTreeObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import com.debug.myapplication.Selectbale.SelectableTextHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.jxxy.debug.common.http.repository.ResourceRepository
import org.jxxy.debug.common.util.BookMarkUtil
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.resources.R
import org.jxxy.debug.resources.activity.markText
import org.jxxy.debug.resources.bean.TextContent
import org.jxxy.debug.resources.databinding.ItemNewTextBriefBinding
import org.jxxy.debug.resources.http.response.ResourceInfoResponse
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.util.goLogin
import org.jxxy.debug.resources.util.isLogin
import java.lang.ref.WeakReference

class NewTextBriefViewHolder(binding: ItemNewTextBriefBinding,val textSelectListen:SelectableTextHelper.OnSelectListener,val viewModel: ResourceViewModel, val activity: WeakReference<LifecycleOwner>,val pageSum: MutableLiveData<String>) : MultipleViewHolder2<ItemNewTextBriefBinding, ResourceInfoResponse>(binding) {
    var adapter: MultipleTypeAdapter?=null
    lateinit var act : Activity
    var count = 0
    var countBook = 0
    override fun setHolder(entity: ResourceInfoResponse) {
        val info = entity.resourceInfo!!
        pageSum.postValue("1")
        SelectableTextHelper.Builder(view.textView)
            .setListener(textSelectListen)
            .build()
        SelectableTextHelper.Builder(view.authorTextView).setListener(textSelectListen)
            .build()
        SelectableTextHelper.Builder(view.tileTextView).setListener(textSelectListen)
            .build()
        view.authorTextView.text = info.resourceAuthorName
        view.authorImageView.load(info.resourceAuthorHead, true)
        view.lableTextView.text = info.resourceLabel
        view.tileTextView.text = info.resourceTitle
        view.likeTextView.text = info.resourceLikes.toString()
        view.readTextView.text = info.resourceReads.toString()
        view.createDateTextView.text = info.createTime
        view.idTv.text = "id-${info.resourceId}"
        view.likeIcon.setTag(view.likeIcon.id,false)
        view.likeIcon.singleClick {icon->
            if ( icon.getTag(icon.id) as Boolean){
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        entity.resourceInfo?.let {it1 -> ResourceRepository.cancelLike(it1.resourceId.toInt())}
                    }?.let {
                        withContext(Dispatchers.Main){
                            if (it.code==0){
                                view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_icon)
                                view.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.bg_300))
                                view.likeTextView.text = it.data.toString()
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
                            entity.resourceInfo?.let {it1 -> ResourceRepository.addLike(it1.resourceId.toInt())}
                        }?.let {
                            withContext(Dispatchers.Main){
                                if (it.code==0){
                                    view.likeIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.like_fill)
                                    view.likeIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                                    view.likeTextView.text = it.data.toString()
                                    view.likeIcon.setTag(icon.id,true)
                                }
                            }
                        }
                    }
                }
            }
        }
        view.markIcon.setTag(view.markIcon.id,false)
        view.markIcon.singleClick(increase = true) { icon->
            if (!isLogin()) {
                goLogin(context)
                return@singleClick
            }
            if ( icon.getTag(icon.id) as Boolean){
                GlobalScope.launch {
                    withContext(Dispatchers.IO){
                        entity.resourceInfo?.let {it1 -> ResourceRepository.cancelCollection(it1.resourceId.toInt())}
                    }?.let {
                        withContext(Dispatchers.Main){
                            if (it.code==0){
                                view.markIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.icon_mark_Hollow)
                                view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.bg_300))
                                view.markIcon.setTag(icon.id,false)
                            }
                        }
                    }
                }
            }
            else {
                GlobalScope.launch {
                    GlobalScope.launch {
                        withContext(Dispatchers.IO){
                            entity.resourceInfo?.let {it1 -> ResourceRepository.addCollection(it1.resourceId.toInt())}
                        }?.let {
                            withContext(Dispatchers.Main){
                                if (it.code==0){
                                    view.markIcon.text = ResourceUtil.getString(org.jxxy.debug.common.R.string.mark)
                                    view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.primary_100))
                                    view.markIcon.setTag(icon.id,true)
                                }
                            }
                        }
                    }
                }
            }
        }
        activity.get()?.let {
            viewModel.addMarkLiveData.observe(it) {
                it.onSuccess {
                    view.markIcon.setTextColor(ResourceUtil.getColor(R.color.pink))
                    entity.myState!!.collectState = 1
                }
            }
            viewModel.cancelMarkLiveData.observe(it) {
                it.onSuccess {
                    view.markIcon.setTextColor(ResourceUtil.getColor(org.jxxy.debug.corekit.R.color.grey))
                    entity.myState!!.collectState = 0
                }
            }
        }
        val textView = view.textView
        textView.text = info.resourceContent
        textView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
            override fun onPreDraw(): Boolean {
                Log.d("onPreDraw", "onPreDraw: 1")
                val totalLineCount = textView.height / textView.lineHeight
                val layout = textView.layout
                val remainingLines = layout.lineCount - totalLineCount
                var textLength=0
                if (remainingLines > 0) {
                    val remainingText = info.resourceContent?.substring(layout.getLineStart(totalLineCount), layout.getLineEnd(textView.layout.lineCount - 1))
                    val substring =
                        info.resourceContent?.substring(0, layout.getLineStart(totalLineCount))
                    textView.text = substring
                    textView.tag = 0 
                    textLength = substring?.length ?: 0
                    remainingText?.let {
                        adapter?.add(TextContent(it, entity.resourceInfo!!.resourceId, textLength,entity.tagInfo,textLength))
                    }
                }else{
                    textLength = textView.text.length
                }
//                textView.tagList = entity.tagInfo
                if (entity.tagInfo?.size?:0>0){
                    entity.tagInfo!!.forEach {
                        if (it.start<textLength){
                            if (it.end<textLength){
                                markText(textView,it.start,it.end)
                            }else{
                                markText(textView,it.start,textLength)
                            }
                        }
                    }
                }
                if(count == 0) {
                    textView.viewTreeObserver.removeOnPreDrawListener(this)
                    val location = IntArray(2)
                    view.lineView.getLocationOnScreen(location)
                    BookMarkUtil.addView(act, location[1])
                    BookMarkUtil.get(entity.resourceInfo?.resourceId!!)
                    count = 1
                    if(countBook == 1) {
                        BookMarkUtil.bookMark.hide()
                    }
                }
                return true
            }
        })
    }
}