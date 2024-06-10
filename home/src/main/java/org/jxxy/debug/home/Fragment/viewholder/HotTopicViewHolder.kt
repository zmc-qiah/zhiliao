package org.jxxy.debug.home.Fragment.viewholder

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.home.Fragment.adapter.HotTopicAdapter
import org.jxxy.debug.home.Fragment.decoration.HottopicSlideBarItemDecoration
import org.jxxy.debug.home.Fragment.item.HotTopic
import org.jxxy.debug.home.databinding.ComponentHotTopicBinding

class HotTopicViewHolder(val binding:ComponentHotTopicBinding,private val lifecycle: Lifecycle) :
MultipleViewHolder<HotTopic>(binding.root){
    private val hotTopicAdapter=HotTopicAdapter()
    private var itemCount=0
    init {
        val layoutManager = GridLayoutManager(itemView.context, 3)
        layoutManager.orientation = RecyclerView.HORIZONTAL
        binding.rvHotTopic.layoutManager = layoutManager
        binding.rvHotTopic.addItemDecoration(HottopicSlideBarItemDecoration(context))
        binding.rvHotTopic.adapter = hotTopicAdapter
    }

    override fun setHolder(entity: HotTopic) {
        val TAG = "confine"
        Log.d(TAG, "hottopicviewholder")
        entity.hotTopic?.let { List ->
            hotTopicAdapter.clear()
            val tools = HotTopic(entity.type,entity.typeName, List)
            for (i in 1..80) {
                hotTopicAdapter.add(tools)
            }
        }
        itemCount = entity.hotTopic?.size ?: 0
        binding.rvHotTopic.smoothScrollToPosition(0)
    }


}