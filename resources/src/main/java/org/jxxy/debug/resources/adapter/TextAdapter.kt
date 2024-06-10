package org.jxxy.debug.resources.adapter

import android.app.Activity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.debug.myapplication.Selectbale.SelectableTextHelper
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.resources.databinding.ItemNewTextBriefBinding
import org.jxxy.debug.resources.databinding.ItemTextComtentBinding
import org.jxxy.debug.resources.http.viewModel.ResourceViewModel
import org.jxxy.debug.resources.util.Mytype
import org.jxxy.debug.resources.viewHolder.NewTextBriefViewHolder
import org.jxxy.debug.resources.viewHolder.NewTextContentViewHolder
import java.lang.ref.WeakReference

class TextAdapter(val viewModel: ResourceViewModel, val textSelectListen: SelectableTextHelper.OnSelectListener, val activity:LifecycleOwner, val pageSum:MutableLiveData<String>) : MultipleTypeAdapter(){
    lateinit var act : Activity
    var count = -1
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (viewType){
        Mytype.ITEM_TEXT_CONTENE -> {
            val holder =
                NewTextContentViewHolder(ItemTextComtentBinding.inflate(inflater, parent, false),
                    textSelectListen,
                    WeakReference(activity as FragmentActivity),
                    pageSum)
            holder.adapter =this
            holder
        }
        Mytype.RESOURCE_TEXT_INFO -> {
            val holder = NewTextBriefViewHolder(
                ItemNewTextBriefBinding.inflate(inflater, parent, false),
                textSelectListen,
                viewModel,
                WeakReference<LifecycleOwner>(activity),
                pageSum
            )
            holder.adapter  =this
            holder.act = act
            holder.countBook = count
            holder
        }
        else -> null
    }
}