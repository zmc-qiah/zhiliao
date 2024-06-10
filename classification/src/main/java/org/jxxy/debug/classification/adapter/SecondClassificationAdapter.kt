package com.jxxy.debug.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jxxy.debug.category.viewholder.SecondCategoryViewHolder
import com.jxxy.debug.category.viewholder.SecondCategoryViewHolder.Companion.STATE_CHECKED
import com.jxxy.debug.category.viewholder.SecondCategoryViewHolder.Companion.STATE_NEXT
import com.jxxy.debug.category.viewholder.SecondCategoryViewHolder.Companion.STATE_NORMAL
import com.jxxy.debug.category.viewholder.SecondCategoryViewHolder.Companion.STATE_PRE
import org.jxxy.debug.classification.databinding.ItemLevel2Binding
import org.jxxy.debug.common.http.Response.SecondCategory
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder

class SecondClassificationAdapter : SingleTypeAdapter<SecondCategory>(), SecondCategoryViewHolder.OnItemClickListener {
    var mOnItemClickListener: OnItemClickListener? = null
    var resourceAdapter : ResourceAdapter? = null
    private var curPosition: Int = 0

    interface OnItemClickListener {
        fun onItemClick(entity: SecondCategory, position: Int)
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener?) {
        mOnItemClickListener = clickListener
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.getOrNull(payloads.lastIndex)?.let {
                bindHolder(holder as SingleViewHolder<ItemLevel2Binding,SecondCategory>, data[position], position,payloads)
            }
        } else {
            val list = ArrayList<Any>()
            list.add(when (position) {
                curPosition -> STATE_CHECKED
                curPosition - 1 -> STATE_PRE
                curPosition + 1 -> STATE_NEXT
                else -> STATE_NORMAL
            })
            bindHolder(
                holder as SingleViewHolder<ItemLevel2Binding,SecondCategory>,
                data[position],
                position,
                list
            )
        }
    }

    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        val holder = SecondCategoryViewHolder(
            ItemLevel2Binding.inflate(inflater, parent, false)
        )
        holder.mOnItemClickListener = this
        return holder as? SingleViewHolder<ViewBinding, Any>
    }

    fun refresh() {
        curPosition = 0
    }

    override fun onItemClick(entity: SecondCategory, position: Int) {
/*        notifyItemRangeChanged(0, itemCount, STATE_NORMAL)
        notifyItemChanged(position, STATE_CHECKED)*/
        curPosition = position
        notifyItemRangeChanged(0, itemCount)
        mOnItemClickListener?.onItemClick(entity, position)
        resourceAdapter?.notifyItemRangeChanged(0,resourceAdapter?.itemCount!! - 1)
    }
}
