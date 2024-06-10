package com.jxxy.debug.category.viewholder

import android.util.Log
import android.view.View
import com.jxxy.debug.category.adapter.ClassificationAdapter
import com.jxxy.debug.category.adapter.SecondClassificationAdapter
import org.jxxy.debug.classification.R
import org.jxxy.debug.classification.databinding.ItemDialogBinding
import org.jxxy.debug.common.http.Response.FirstCategory
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick

class DialogViewHolder(view: ItemDialogBinding) :
    SingleViewHolder<ItemDialogBinding, FirstCategory>(view) {

    companion object {
        const val STATE_NORMAL = 0
        const val STATE_CHECKED = 1
    }

    var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(entity: FirstCategory, position: Int)
    }

    override fun setHolder(entity: FirstCategory) {
        view.level1Text.text = entity.classifyName
        view.root.singleClick {
            val firstCategoryAdapter = ClassificationAdapter()
            val secondCategoryAdapter = SecondClassificationAdapter()
            entity.list?.let {
//                firstCategoryAdapter.refreshSecondCategory(position)
                secondCategoryAdapter.clear()
                secondCategoryAdapter.add(it)
            }
            mOnItemClickListener?.onItemClick(entity,absoluteAdapterPosition)
        }
    }

    override fun setHolder(entity: FirstCategory, payload: Any) {
        when (payload) {
            STATE_NORMAL -> {
                onNormalState()
            }
            STATE_CHECKED -> {
                onCheckedState()
            }
        }
        setHolder(entity)
    }

    fun onNormalState() {
        view.level1Text.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
        view.underline.visibility = View.INVISIBLE
    }

    fun onCheckedState() {
        view.level1Text.setTextColor(ResourceUtil.getColor(R.color.light_blue苍苍))
        view.underline.visibility = View.VISIBLE
    }
}
