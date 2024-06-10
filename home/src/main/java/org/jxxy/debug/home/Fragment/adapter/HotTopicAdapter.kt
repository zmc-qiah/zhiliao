package org.jxxy.debug.home.Fragment.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.home.Fragment.http.viewholder.HotTopicItemViewHolder
import org.jxxy.debug.home.Fragment.item.HotTopic
import org.jxxy.debug.home.Fragment.item.bean.HotTopicBean
import org.jxxy.debug.home.databinding.ItemHotTopicBinding

class HotTopicAdapter :SingleTypeAdapter<HotTopic>(){
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? {
        val TAG = "confine"
        Log.d(TAG, "hottopicadapter---3")
        return HotTopicItemViewHolder(ItemHotTopicBinding.inflate(inflater,parent,false))
    }
    override fun getItemCount(): Int {
        return 9
    }

    override fun getItemViewType(position: Int): Int {
        return 0
    }


}