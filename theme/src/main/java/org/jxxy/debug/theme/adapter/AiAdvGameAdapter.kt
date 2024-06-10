package org.jxxy.debug.theme.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.theme.MyType
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.bean.ChatContent
import org.jxxy.debug.theme.databinding.ItemAiAdvAssitantBinding
import org.jxxy.debug.theme.databinding.ItemAiAdvUserBinding

class AiAdvGameAdapter : MultipleTypeAdapter() {
    val userHead : String = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/04/785d93dd-5653-4d8e-9814-655abb26a7ba.jpg"
    val assistantHead : String = "https://bucket-lsp.oss-cn-hangzhou.aliyuncs.com/2023/09/10/a795b4b1-a1e4-4484-a3be-23375c991f13.png"
    var type = -1
    var way : () -> Unit = {

    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return when(viewType){
            MyType.CHAT_USER -> UserViewHolder(ItemAiAdvUserBinding.inflate(inflater,parent,false))
            MyType.CHAT_ASSISTANT -> AssistantViewHolder(ItemAiAdvAssitantBinding.inflate(inflater,parent,false))
            else -> null
        }
    }

    inner class AssistantViewHolder(view : ItemAiAdvAssitantBinding) : MultipleViewHolder2<ItemAiAdvAssitantBinding,ChatContent>(view){
        override fun setHolder(entity: ChatContent) {
            if(type != -1){
                way()
                view.aiAdvAssistantTv.setTextColor(Color.parseColor("#C0000000"))
                view.aiAdvAssistantTv.setBackgroundResource(R.drawable.background_item_chat_knowledge)
            }
            view.aiAdvAssistantHeadIv.load(assistantHead,2)
            view.aiAdvAssistantTv.text = entity.content
        }
    }

    inner class UserViewHolder(view : ItemAiAdvUserBinding) : MultipleViewHolder2<ItemAiAdvUserBinding,ChatContent>(view){
        override fun setHolder(entity: ChatContent) {
            if(type != -1){
                way()
                view.aiAdvUserTv.setTextColor(Color.parseColor("#C0000000"))
                view.aiAdvUserTv.setBackgroundResource(R.drawable.background_item_chat_knowledge)
            }
            view.aiAdvUserIv.load(userHead,2)
            view.aiAdvUserTv.text = entity.content
        }
    }
}