package org.jxxy.debug.society.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.society.databinding.ItemAibreaklowBinding
import org.jxxy.debug.society.databinding.ItemAnswerAnswerBinding
import org.jxxy.debug.society.databinding.ItemAnswerQuestionBinding
import org.jxxy.debug.society.databinding.ItemCreatorBinding
import org.jxxy.debug.society.databinding.ItemStudylowBinding
import org.jxxy.debug.society.holder.AibreaklowHolder
import org.jxxy.debug.society.holder.AnswerHolder
import org.jxxy.debug.society.holder.CreatorHolder
import org.jxxy.debug.society.holder.QuestionHolder
import org.jxxy.debug.society.holder.StudeylowHolder

class LegalAdapter: MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {

        return when (viewType) {

            0 -> AibreaklowHolder (ItemAibreaklowBinding.inflate(inflater))
            2 -> CreatorHolder (
                ItemCreatorBinding.inflate(
                    inflater
                )
            )
            1 ->StudeylowHolder(ItemStudylowBinding.inflate(inflater))
            else -> null
        }
    }

}
