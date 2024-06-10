package org.jxxy.debug.theme.viewHolder

import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.databinding.ItemChatAssistantBinding
import org.jxxy.debug.theme.myListener.ChatItemClick

class ChatViewHolder(var view: ItemChatAssistantBinding,var listener : ChatItemClick?= null) : MultipleViewHolder<ChatContent>(view.root) {
    override fun setHolder(entity: ChatContent) {
        view.assistantTV.text = entity.content
        view.assistantIV.load(entity.picture,true)
        view.againIcon.singleClick {
            listener?.onClick(entity.role,entity,absoluteAdapterPosition)
        }
        view.againTextView.singleClick {
            listener?.onClick(entity.role,entity,absoluteAdapterPosition)
        }
    }
}
