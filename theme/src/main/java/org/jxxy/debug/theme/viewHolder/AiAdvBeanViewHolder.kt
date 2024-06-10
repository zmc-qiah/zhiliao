package org.jxxy.debug.theme.viewHolder

import com.bumptech.glide.Glide
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.theme.bean.AiAdvBean
import org.jxxy.debug.theme.databinding.ItemAdvItemBinding

class AiAdvBeanViewHolder(view: ItemAdvItemBinding) :
    SingleViewHolder<ItemAdvItemBinding, AiAdvBean>(view) {
    override fun setHolder(entity: AiAdvBean) {
        view.atv.text = entity.name
//        Glide.with(view.bgView).load(entity.photo).into(view.bgView)
        view.bgView.load(entity.photo)
    }
}