package org.jxxy.debug.resources.adapter

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.nullOrNot
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.resources.bean.CommentInfo
import org.jxxy.debug.resources.bean.ItemCommentedChapter
import org.jxxy.debug.resources.databinding.ItemChapterReviewBinding
import org.jxxy.debug.resources.databinding.ItemCommentedTextBinding
import org.jxxy.debug.resources.util.Mytype

class ChapterCommentAdapter: MultipleTypeAdapter() {
    fun addNot(list: List<MultipleType>):Boolean{
        var tempList: ArrayList<MultipleType> ?=null
        list.forEach {it1->
            var b = true
            for (datum in data) {
                if (datum.equals(it1)){
                    b = false
                    break
                }
            }
            if (b) tempList.nullOrNot(
                {
                    tempList = ArrayList<MultipleType>()
                    tempList!!.add(it1)
                },{
                    tempList!!.add(it1)
                }
            )
        }
        add(tempList)
        return tempList!=null
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (viewType) {
        Mytype.ITEM_Commented_Chapter ->object :
            MultipleViewHolder2<ItemCommentedTextBinding, ItemCommentedChapter>(
                ItemCommentedTextBinding.inflate(inflater,parent,false)){
            override fun setHolder(entity: ItemCommentedChapter) {
                view.copyIcon.singleClick {
                    // 获取剪贴板管理器
                    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                    val clipData = ClipData.newPlainText("label", entity.content)
                    "复制成功".toast()
                    clipboardManager.setPrimaryClip(clipData)
                }
                view.textView.text = entity.content
            }
        }
        Mytype.ITEM_Chapter_Comment -> object :
            MultipleViewHolder2<ItemChapterReviewBinding, CommentInfo>(ItemChapterReviewBinding.inflate(inflater,parent,false)){
            override fun setHolder(entity: CommentInfo) {
                view.textView.isEnabled = false
                view.textView.setText(entity.comment)
                view.userTV.text = entity.userInfo.userName
                view.userIV.load(entity.userInfo.headPhoto,true)
                view.likeTV.text = entity.commentLikes.toString()
                view.markTV.text = entity.commentLikes.toString()
            }
        }
        else ->{null}
    }
}