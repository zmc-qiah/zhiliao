package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.member.bean.UserDetail
import org.jxxy.debug.member.databinding.ItemDetailImgBinding

class UserDetailImgViewHolder(binding: ItemDetailImgBinding) : MultipleViewHolder2<ItemDetailImgBinding, UserDetail>(binding) {
    override fun setHolder(entity: UserDetail) {
        view.textView.text = entity.name
        view.imageView.load(entity.value, true)
    }
}
