package org.jxxy.debug.theme.viewHolder


import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.databinding.ItemChatUserBinding
import org.jxxy.debug.theme.myListener.ChatItemClick

class UserViewHolder(var view: ItemChatUserBinding,val listener : ChatItemClick?= null) : MultipleViewHolder<ChatContent>(view.root) {
    override fun setHolder(entity: ChatContent) {
        view.userTV.text = entity.content
        view.userIV.load(entity.picture,true)
        view.modifyIcon.singleClick {
            listener?.onClick(entity.role,entity,absoluteAdapterPosition)
        }
        view.modifyTextView.singleClick {
            listener?.onClick(entity.role,entity,absoluteAdapterPosition)
        }
    }
}
