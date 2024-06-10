package org.jxxy.debug.resources.viewHolder

import android.view.ViewTreeObserver
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.MutableLiveData
import com.debug.myapplication.Selectbale.SelectableTextHelper
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.resources.activity.markText
import org.jxxy.debug.resources.bean.TextContent
import org.jxxy.debug.resources.databinding.ItemTextComtentBinding
import java.lang.ref.WeakReference

class NewTextContentViewHolder(binding: ItemTextComtentBinding,val textSelectListen:SelectableTextHelper.OnSelectListener,val activity: WeakReference<FragmentActivity>,val pageSum: MutableLiveData<String>) : MultipleViewHolder2<ItemTextComtentBinding, TextContent>(binding) {
    var adapter: MultipleTypeAdapter?=null
    override fun setHolder(entity: TextContent) {
        SelectableTextHelper.Builder(view.textView)
            .setListener(textSelectListen)
            .build()
        if(!"remainingText".equals(entity.content)){
            view.textView.text = entity.content
            val textView = view.textView
            textView.viewTreeObserver.addOnPreDrawListener(object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    val totalLineCount = textView.height / textView.lineHeight
                    val layout = textView.layout
                    val remainingLines = layout.lineCount - totalLineCount
                    var textLength = entity.baseOffset + textView.text.length
                    if (adapter?.itemCount==2 && totalLineCount!=0){
                        pageSum.postValue((((remainingLines + totalLineCount - 1) / totalLineCount) + 2).toString())
                    }
                    view.textView.tag = entity.baseOffset
                    if (remainingLines > 0) {
                        val remainingText = entity.content.substring(layout.getLineStart(totalLineCount), layout.getLineEnd(layout.lineCount - 1))
                        val substring =
                            entity.content.substring(0, layout.getLineStart(totalLineCount))
                         textLength = substring.length+entity.baseOffset
                        textView.text = substring
                        adapter?.add(TextContent(remainingText,entity.id, textLength,entity.tagInfo,textLength))
                    }
//                    textView.tagList = entity.tagInfo
                    if (entity.tagInfo?.size?:0>0){
                        entity.tagInfo!!.forEach {
                            if (it.start>entity.baseOffset){
                                if (it.end<textLength){
                                    markText(textView,it.start - entity.baseOffset,it.end - entity.baseOffset)
                                }else if (it.start<textLength){
                                    markText(textView,it.start - entity.baseOffset ,textLength - entity.baseOffset)
                                }
                            }
                        }
                    }
                    textView.viewTreeObserver.removeOnPreDrawListener(this)
                    return true
                }
            })
        }

    }
}