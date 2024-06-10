package com.jxxy.debug.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jxxy.debug.category.viewholder.DialogViewHolder
import org.jxxy.debug.classification.databinding.ItemDialogBinding
import org.jxxy.debug.common.http.Response.FirstCategory
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder

class ClassificationDialogAdapter : SingleTypeAdapter<FirstCategory>(), DialogViewHolder.OnItemClickListener {

    var nowChoose = 0
    private var mOnItemClickListener: OnItemClickListener? = null


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.getOrNull(payloads.lastIndex)?.let {
                bindHolder(holder as SingleViewHolder<ItemDialogBinding,FirstCategory>, data[position], position,payloads)
            }
        } else {
            val list = ArrayList<Any>()
            list.add(if(nowChoose == position){
                DialogViewHolder.STATE_CHECKED
            }else{
                DialogViewHolder.STATE_NORMAL
            })
            bindHolder(holder as SingleViewHolder<ItemDialogBinding,FirstCategory>, data[position], position,list)
        }
    }

    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        val view = ItemDialogBinding.inflate(
            inflater,
            parent,
            false
        )
        val holder = DialogViewHolder(
            view
        )
        holder.mOnItemClickListener = this
        return holder as? SingleViewHolder<ViewBinding, Any>
    }

    interface OnItemClickListener {
        fun onItemClick(entity: FirstCategory, position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener?) {
        mOnItemClickListener = clickListener
    }

    override fun onItemClick(entity: FirstCategory, position: Int) {
//        notifyItemRangeChanged(0, itemCount, DialogViewHolder.STATE_NORMAL)
//        notifyItemChanged(position, DialogViewHolder.STATE_CHECKED)
        nowChoose = position
        mOnItemClickListener?.onItemClick(entity, position)
    }
}
