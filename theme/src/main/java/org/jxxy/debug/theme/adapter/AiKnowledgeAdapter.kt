package org.jxxy.debug.theme.adapter

import android.animation.ObjectAnimator
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.theme.R
import org.jxxy.debug.theme.databinding.ItemAiKnowledgeTextBinding

class AiKnowledgeAdapter : SingleTypeAdapter<String>() {
    val list
        get() = data
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return ItemAiKnowledgeViewHolder(ItemAiKnowledgeTextBinding.inflate(inflater,parent,false))
    }

    inner class ItemAiKnowledgeViewHolder(view : ItemAiKnowledgeTextBinding) : SingleViewHolder<ItemAiKnowledgeTextBinding,String>(view){
        override fun setHolder(entity: String) {
            view.aiKnowledgeTv.text = entity
            when(layoutPosition % 3) {
                0 -> view.aiKnowledgeTv.setBackgroundResource(R.drawable.item_ai_knowledge_first)
                1 -> view.aiKnowledgeTv.setBackgroundResource(R.drawable.item_ai_knowledge_second)
                2 -> {
                    view.aiKnowledgeTv.setBackgroundResource(R.drawable.item_ai_knowledge_third)
//                    view.aiKnowledgeTv.setTextColor(ResourceUtil.getColor(org.jxxy.debug.common.R.color.black))
                }
            }
        }

        override fun setHolder(entity: String, payload: Any) {
            Log.d("payload", "setHolder: dasdsadsadsd")
            if (payload as? Boolean ?: false){
                ObjectAnimator.ofFloat(
                    view.aiKnowledgeTv,"alpha",0f,1f
                ).apply {
                    duration = 400
                    start()
                }
            }else{
                view.aiKnowledgeTv.alpha = 0f
            }
        }
    }
}