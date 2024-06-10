package org.jxxy.debug.home.Fragment.viewholder

import android.util.Log
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.SpanItemDecoration
import org.jxxy.debug.corekit.util.ScheduleTask
import org.jxxy.debug.home.Fragment.adapter.PostureAdapter
import org.jxxy.debug.home.Fragment.item.Posture
import org.jxxy.debug.home.databinding.ComponentNewsBinding
import java.util.concurrent.TimeUnit
class PostureViewHolder(val binding: ComponentNewsBinding, private val lifecycle: Lifecycle) :
    MultipleViewHolder<Posture>(binding.root) {
    private val dataList: MutableList<Posture> = mutableListOf()
    private var currentPosition = 0
    private var isPlaying = false

    private var adapter: PostureAdapter? = null
    private val layoutManager: GridLayoutManager

    init {
        adapter = PostureAdapter()
        layoutManager = GridLayoutManager(itemView.context, 4)
        layoutManager.isSmoothScrollbarEnabled = false
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.addItemDecoration(SpanItemDecoration(20f, 7f, 4))
        binding.recyclerview.adapter = adapter

        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                    val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()

                    if (currentPosition < firstVisibleItemPosition || currentPosition > lastVisibleItemPosition) {
                        currentPosition = firstVisibleItemPosition
                        binding.recyclerview.smoothScrollToPosition(currentPosition)
                    }
                }
            }
        })

        ScheduleTask(lifecycle, 3, TimeUnit.SECONDS) {
            if (isPlaying && dataList.size > 0) {
                currentPosition = (currentPosition + 1) % dataList.size
                binding.recyclerview.smoothScrollToPosition(currentPosition)
                Log.d("subscriaabe", "currentItem $currentPosition")
            }
           }.startTask()
    }

    override fun setHolder(entity: Posture) {
        binding.newsTitle.text = entity.typeName
        entity.charmInfoList?.let { list ->
            dataList.clear()
            for (i in 1..80) {
                val tools = Posture(entity.type, entity.typeName,list)
                dataList.add(tools)
            }
            adapter?.add(dataList)
            adapter?.notifyDataSetChanged()
            currentPosition = 0
            binding.recyclerview.scrollToPosition(currentPosition)
            isPlaying = true
        }
        Log.d("PostureViewHolder", "Data list size: ${dataList.size}")
        Log.d("PostureViewHolder", "Current position: $currentPosition")
        Log.d("PostureViewHolder", "Adapter item count: ${adapter?.itemCount}")
    }
}