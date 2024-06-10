package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.society.databinding.ItemAnswerAnswerBinding
import org.jxxy.debug.society.databinding.ItemAnswerQuestionBinding
import org.jxxy.debug.society.databinding.ItemClassBinding
import org.jxxy.debug.society.databinding.ItemExcellentintroduceBinding
import org.jxxy.debug.society.databinding.ItemMyclassBinding
import org.jxxy.debug.society.databinding.ItemRecommendclassBinding
import org.jxxy.debug.society.holder.AnswerHolder
import org.jxxy.debug.society.holder.ExcellentIntroduceHolder
import org.jxxy.debug.society.holder.ExcellentclassHolder
import org.jxxy.debug.society.holder.MyclassHolder
import org.jxxy.debug.society.holder.QuestionHolder
import org.jxxy.debug.society.holder.RecommendclassHolder

class ExcellentClassAdapter : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {

        return when (viewType) {

            0 -> ExcellentclassHolder (ItemClassBinding .inflate(inflater))
            1 -> RecommendclassHolder (
                ItemRecommendclassBinding.inflate(
                    inflater
                )
            )
            2->MyclassHolder(ItemMyclassBinding.inflate(inflater))
            3->ExcellentIntroduceHolder(ItemExcellentintroduceBinding.inflate(inflater))
            else -> null
        }
    }


}