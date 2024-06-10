package org.jxxy.debug.theme.viewHolder

import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.theme.bean.ThemeAIPaintBean
import org.jxxy.debug.theme.databinding.ItemPaintItemBinding

class PaintViewHolder(binding: ItemPaintItemBinding) :
        SingleViewHolder<ItemPaintItemBinding, ThemeAIPaintBean>(binding) {
        override fun setHolder(entity: ThemeAIPaintBean) {
           view.aIv.load(entity.photo,16)
            view.aIv.parent.requestDisallowInterceptTouchEvent(true)
            view.titleTV.text = "该图片提示词为:"+entity.tag
        }
    }