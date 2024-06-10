package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.item.Interview
import org.jxxy.debug.home.Fragment.item.Learn
import org.jxxy.debug.home.Fragment.viewholder.InterviewItemViewHolder
import org.jxxy.debug.home.Fragment.viewholder.LearnItemViewHolder
import org.jxxy.debug.home.databinding.ItemInterviewBinding
import org.jxxy.debug.home.databinding.ItemLearnBinding

class LearnAdapter  : SingleTypeAdapter<Learn>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return LearnItemViewHolder(
            ItemLearnBinding.inflate(
                inflater,
                parent,
                false
            )
        ) as? SingleViewHolder<ViewBinding, Any>
    }
    override fun getItemCount(): Int {
        return 4
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
