package org.jxxy.debug.thinkMap.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jxxy.debug.common.databinding.ItemPictureBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.thinkMap.MyListener.OnItemClickListener
import org.jxxy.debug.thinkMap.databinding.ItemPreviewNodePictureBinding

class ThinkMapPicturesAdapter(val type :Int= 0) : SingleTypeAdapter<String>() {
    var listener: OnItemClickListener? = null
    val list: List<String>
        get() { return data }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (type) {
        1->{
            Picture200ViewHolder(ItemPreviewNodePictureBinding.inflate(inflater, parent, false), listener)
        }
        else ->{
            Picture60ViewHolder(ItemPictureBinding.inflate(inflater, parent, false), listener)
        }
    }
    class Picture60ViewHolder(binding: ItemPictureBinding, val listener: OnItemClickListener?) : SingleViewHolder<ItemPictureBinding, String>(binding) {
        override fun setHolder(entity: String) {
            view.imageView.load(entity,6)
            var startX = 0f
            var startY = 0f
            var isDragging = false
            view.imageView.setOnTouchListener { v, event ->
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        startX = event.x
                        startY = event.y
                        isDragging = false
                    }
                    MotionEvent.ACTION_MOVE -> {
                        val distanceX = Math.abs(event.x - startX)
                        val distanceY = Math.abs(event.y - startY)
                        if (distanceX > 10 || distanceY > 10) {
                            isDragging = true
                        }
                    }
                    MotionEvent.ACTION_UP -> {
                        if (!isDragging) {
                            "aa".toast()
                        } else {
                        }
                        isDragging = false
                    }
                }
                false
            }
        }
    }
    class Picture200ViewHolder(binding: ItemPreviewNodePictureBinding, val listener: OnItemClickListener?) : SingleViewHolder<ItemPreviewNodePictureBinding, String>(binding) {
        override fun setHolder(entity: String) {
            view.imageView.load(entity,6)
            view.imageView.singleClick {
                listener?.onItemClick(it,layoutPosition)
            }
        }
    }
}
