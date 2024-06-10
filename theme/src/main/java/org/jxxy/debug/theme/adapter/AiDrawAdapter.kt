package org.jxxy.debug.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.theme.bean.AiDrawItem
import org.jxxy.debug.theme.databinding.ItemAiDrawEditBinding
import org.jxxy.debug.theme.databinding.ItemAiDrawImageBinding

class AiDrawAdapter : MultipleTypeAdapter() {
    lateinit var wayImage : (String) -> Unit
    lateinit var wayEdit : () -> String
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        return when(viewType){
            1 -> AiImageViewHolder(ItemAiDrawImageBinding.inflate(inflater,parent,false))
            2 -> AiEditViewHolder(ItemAiDrawEditBinding.inflate(inflater,parent,false))
            else -> null
        }
    }

    inner class AiImageViewHolder(view : ItemAiDrawImageBinding) : MultipleViewHolder2<ItemAiDrawImageBinding,AiDrawItem>(view){
        override fun setHolder(entity: AiDrawItem) {
            wayImage = {
                view.aiDrawIv.load(it,5)
            }
        }
    }

    inner class AiEditViewHolder(view : ItemAiDrawEditBinding) : MultipleViewHolder2<ItemAiDrawEditBinding,AiDrawItem>(view){
        override fun setHolder(entity: AiDrawItem) {
            wayEdit = {
               view.aiDrawEdit.text.toString()
            }
        }
    }
}