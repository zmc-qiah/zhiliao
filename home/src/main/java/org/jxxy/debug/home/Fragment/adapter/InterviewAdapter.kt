package org.jxxy.debug.home.Fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.home.Fragment.http.viewholder.HotTopicItemViewHolder
import org.jxxy.debug.home.Fragment.item.Book
import org.jxxy.debug.home.Fragment.item.HotTopic
import org.jxxy.debug.home.Fragment.item.Interview
import org.jxxy.debug.home.Fragment.viewholder.BookItemViewHolder
import org.jxxy.debug.home.Fragment.viewholder.InterviewItemViewHolder
import org.jxxy.debug.home.databinding.ItemBooksBinding
import org.jxxy.debug.home.databinding.ItemHotTopicBinding
import org.jxxy.debug.home.databinding.ItemInterviewBinding

class InterviewAdapter  : SingleTypeAdapter<Interview>() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): SingleViewHolder<ViewBinding, Any>? {
        return InterviewItemViewHolder(
            ItemInterviewBinding.inflate(
                inflater,
                parent,
                false
            )
        ) as? SingleViewHolder<ViewBinding, Any>
    }
    override fun getItemCount(): Int {
        return 1
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }
}
