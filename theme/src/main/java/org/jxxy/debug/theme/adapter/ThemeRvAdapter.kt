package org.jxxy.debug.theme.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.theme.MyType
import org.jxxy.debug.theme.databinding.*
import org.jxxy.debug.theme.viewHolder.*
import org.jxxy.debug.theme.databinding.ItemAiAdvBinding
import org.jxxy.debug.theme.databinding.ItemAiNavigationBinding
import org.jxxy.debug.theme.databinding.ItemAiPaintBinding
import org.jxxy.debug.theme.databinding.ItemOcrBinding
import org.jxxy.debug.theme.databinding.ItemThemeEmoBinding
import org.jxxy.debug.theme.databinding.ItemThemeOcrBinding
import org.jxxy.debug.theme.databinding.ItemThemePoseBinding
import org.jxxy.debug.theme.viewHolder.AiAdvBeanViewHolder
import org.jxxy.debug.theme.viewHolder.AiAdvViewHolder
import org.jxxy.debug.theme.viewHolder.AiEmoViewHolder
import org.jxxy.debug.theme.viewHolder.AiNavigationViewHolder
import org.jxxy.debug.theme.viewHolder.AiOcrViewHolder
import org.jxxy.debug.theme.viewHolder.AiPaintViewHolder
import org.jxxy.debug.theme.viewHolder.AiPoseViewHolder

class ThemeRvAdapter:MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? =when (viewType) {
        MyType.AI_PAINT ->{
            AiPaintViewHolder(ItemAiPaintBinding.inflate(inflater,parent,false))
        }
        MyType.AI_NAVIGATION ->{
            AiNavigationViewHolder(ItemAiNavigationBinding.inflate(inflater,parent,false))
        }
        MyType.AI_POSE ->{
            AiPoseViewHolder(ItemThemePoseBinding.inflate(inflater,parent,false))
        }
        MyType.AI_EMO ->{
            AiEmoViewHolder(ItemThemeEmoBinding.inflate(inflater,parent,false))
        }
        MyType.AI_OCR ->{
            AiOcrViewHolder(ItemThemeOcrBinding.inflate(inflater,parent,false))
        }
        MyType.AI_KNOWLEDGE ->{
            AiKnowledgeViewHolder(ItemAiKnowledgeBinding.inflate(inflater,parent,false))
        }
        MyType.AI_GUESS_GAME ->{
            AiGuessGameViewHolder(ItemAiGuessGameBinding.inflate(inflater,parent,false))
        }
        MyType.AI_ADV ->{
            AiAdvViewHolder(ItemAiAdvBinding.inflate(inflater,parent,false))
        }
        else -> AiPaintViewHolder(ItemAiPaintBinding.inflate(inflater,parent,false))
    }
}