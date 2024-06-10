package com.jxxy.debug.category.viewholder

import android.content.Context
import android.util.Log
import android.view.View
import org.jxxy.debug.classification.R
import org.jxxy.debug.classification.databinding.ItemLevel1Binding
import org.jxxy.debug.common.http.Response.FirstCategory
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.singleClick

class FirstCategoryViewHolder(view: ItemLevel1Binding) : SingleViewHolder<ItemLevel1Binding, FirstCategory>(view) {

    companion object {
        const val STATE_NORMAL = 0
        const val STATE_CHECKED = 1
    }

    var mOnItemClickListener: OnItemClickListener? = null
    var state = STATE_NORMAL

    interface OnItemClickListener {
        fun onItemClick(entity: FirstCategory, position: Int)
    }

    override fun setHolder(entity: FirstCategory, payload: Any) {
        when (payload) {
            STATE_NORMAL -> {
                onNormalState(entity)
            }
            STATE_CHECKED -> {
                onCheckedState(entity)
            }
        }
    }


    override fun setHolder(entity: FirstCategory) {
        view.level1Text.text = entity.classifyName
        view.root.singleClick {
            entity.list?.let {
                mOnItemClickListener?.onItemClick(entity, layoutPosition)
            }
        }
    }

    fun onNormalState(entity: FirstCategory) {
        view.level1Text.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
        Log.d("TestTest","我进入了NORMAL")
        view.underline.visibility = View.INVISIBLE
        setHolder(entity)
    }

    fun onCheckedState(entity: FirstCategory) {
        view.level1Text.setTextColor(ResourceUtil.getColor(R.color.light_blue苍苍))
        Log.d("TestTest","我进入了CHECK")
        view.underline.visibility = View.VISIBLE
        setHolder(entity)
    }
}
