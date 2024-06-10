package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.society.holder.AnswerHolder
import org.jxxy.debug.society.holder.QuestionHolder
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.society.databinding.ItemAnswerAnswerBinding
import org.jxxy.debug.society.databinding.ItemAnswerQuestionBinding

class AnswerAdapter : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {

        return when (viewType) {

            0 -> AnswerHolder(ItemAnswerAnswerBinding.inflate(inflater))
            1 -> QuestionHolder(
                ItemAnswerQuestionBinding.inflate(
                    inflater
                )
            )
            else -> null
        }
    }


}
