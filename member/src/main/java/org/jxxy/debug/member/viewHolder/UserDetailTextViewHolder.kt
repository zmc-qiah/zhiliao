package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.member.bean.UserDetail
import org.jxxy.debug.member.databinding.ItemDetailTextBinding

class UserDetailTextViewHolder(binding: ItemDetailTextBinding, val helper: UserDetaliHelper) : MultipleViewHolder2<ItemDetailTextBinding, UserDetail>(binding) {
    override fun setHolder(entity: UserDetail) {
        view.textView.text = entity.name
        view.editText.setText(entity.value ?: "")
        view.editText.setOnFocusChangeListener { v, hasFocus ->
            if (!hasFocus) {
                helper.updateInfo(entity, view.editText.text.toString())
            }
        }
    }
}
