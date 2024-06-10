package org.jxxy.debug.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.theme.MyType
import org.jxxy.debug.theme.databinding.ItemChatAssistantBinding
import org.jxxy.debug.theme.databinding.ItemChatUserBinding
import org.jxxy.debug.theme.myListener.ChatItemClick
import org.jxxy.debug.theme.viewHolder.ChatViewHolder
import org.jxxy.debug.theme.viewHolder.UserViewHolder

class ChatAdapter : MultipleTypeAdapter(){
    val list:List<MultipleType>
        get(){return data}
    var listener : ChatItemClick ?= null
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when(viewType){
        MyType.CHAT_ASSISTANT -> ChatViewHolder(ItemChatAssistantBinding.inflate(inflater,parent,false),listener)
        MyType.CHAT_TRANSLATE -> ChatViewHolder(ItemChatAssistantBinding.inflate(inflater,parent,false),listener)
        MyType.CHAT_USER -> UserViewHolder(ItemChatUserBinding.inflate(inflater,parent,false),listener)
        else -> null
    }
}