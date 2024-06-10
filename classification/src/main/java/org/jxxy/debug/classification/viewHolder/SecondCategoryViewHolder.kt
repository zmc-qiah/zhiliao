package com.jxxy.debug.category.viewholder

import android.content.Context
import android.graphics.Color
import org.jxxy.debug.classification.R
import org.jxxy.debug.classification.databinding.ItemLevel2Binding
import org.jxxy.debug.common.http.Response.SecondCategory
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.ResourceUtil
import org.jxxy.debug.corekit.util.hide
import org.jxxy.debug.corekit.util.show
import org.jxxy.debug.corekit.util.singleClick

class SecondCategoryViewHolder(view: ItemLevel2Binding) :
    SingleViewHolder<ItemLevel2Binding, SecondCategory>(view) {

    companion object {
        const val STATE_NORMAL = 0
        const val STATE_CHECKED = 1
        const val STATE_PRE = 2
        const val STATE_NEXT = 3
    }

    var mOnItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(entity: SecondCategory, position: Int)
    }

    override fun setHolder(entity: SecondCategory, payload: Any) {
        view.level2.setRadius(0f)
        view.root.setBackgroundColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.white))
        when (payload) {
            STATE_NORMAL -> {
                onNormalState(entity)
            }
            STATE_CHECKED -> {
                onCheckedState(entity)
            }
            STATE_PRE -> {
                view.level2.setRadiusBottomRight(30f)
                onNormalState(entity)
            }
            STATE_NEXT -> {
                view.level2.setRadiusTopRight(30f)
                onNormalState(entity)
            }
        }
    }

    override fun setHolder(entity: SecondCategory) {
        view.level2Text.text = entity.classifyName
        view.root.singleClick {
            mOnItemClickListener?.onItemClick(entity, layoutPosition)
        }
    }

    private fun onNormalState(entity: SecondCategory) {
        view.level2Text.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
        view.level2.setBackgroundColor((Color.parseColor("#F5F6FA")))
        view.underline.hide()
        setHolder(entity)
    }

    private fun onCheckedState(entity: SecondCategory) {
        view.level2Text.setTextColor(ResourceUtil.getColor(R.color.light_blue苍苍))
        view.level2.setBackgroundColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.white))
        view.underline.show()
        setHolder(entity)
    }
}
