package com.jxxy.debug.category.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.jxxy.debug.category.viewholder.FirstCategoryViewHolder
import org.jxxy.debug.classification.databinding.ItemLevel1Binding
import org.jxxy.debug.common.http.Response.FirstCategory
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder

class ClassificationAdapter : SingleTypeAdapter<FirstCategory>(), FirstCategoryViewHolder.OnItemClickListener {

//    var mOnItemClickListener: OnItemClickListener? = null

//    interface OnItemClickListener {
//        fun onItemClick(entity: FirstCategory, position: Int)
//    }
//
//    fun setOnItemClickListener(clickListener: OnItemClickListener?) {
//        mOnItemClickListener = clickListener
//    }

    var refreshSecondCategory: RefreshSecondCategory? = null

    interface RefreshSecondCategory {
        fun refreshSecondCategory(entity: FirstCategory, position: Int)
    }

    fun refreshSecondCategory(clickListener: RefreshSecondCategory) {
        refreshSecondCategory = clickListener
    }


    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            payloads.getOrNull(payloads.lastIndex)?.let {
                bindHolder(holder as SingleViewHolder<ItemLevel1Binding,FirstCategory>, data[position], position,payloads)
            }
        } else {
            val list = ArrayList<Any>()
            list.add(if (position == curPosition) FirstCategoryViewHolder.STATE_CHECKED else FirstCategoryViewHolder.STATE_NORMAL)
            bindHolder(
                holder as SingleViewHolder<ItemLevel1Binding,FirstCategory>,
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
        val holder = FirstCategoryViewHolder(
            ItemLevel1Binding.inflate(inflater, parent, false)
        )
        holder.mOnItemClickListener = this
        return holder as? SingleViewHolder<ViewBinding, Any>
    }

    private var curPosition: Int? = null

    override fun onItemClick(entity: FirstCategory, position: Int) {
//        notifyItemRangeChanged(0, itemCount, FirstCategoryViewHolder.STATE_NORMAL)
//        notifyItemChanged(position, FirstCategoryViewHolder.STATE_CHECKED)
        notifyItemRangeChanged(0, itemCount)
        curPosition = position
        refreshSecondCategory?.refreshSecondCategory(entity, position)
    }

    fun refreshFirstCategory(position: Int) {
        (data.getOrNull(position) as? FirstCategory)?.let {
            onItemClick(it, position)
        }
    }
}
