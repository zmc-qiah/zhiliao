package org.jxxy.debug.thinkMap.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luck.picture.lib.entity.LocalMedia
import org.jxxy.debug.common.databinding.ItemPictureBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.thinkMap.MyListener.OnItemClickListener
import org.jxxy.debug.thinkMap.databinding.ItemPreviewNodePictureBinding

// 这些地方早期写的很混乱，这个是编辑时候的照片用的，但是很多是用不到的
class PicturesAdapter(val flag :Boolean= false,val type :Int= 0) : SingleTypeAdapter<LocalMedia>() {
    var listener: OnItemClickListener? = null
    val list: List<LocalMedia>
        get() { return data }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = when (type) {
        1->{
            Picture200ViewHolder(ItemPreviewNodePictureBinding.inflate(inflater, parent, false), listener,flag)
        }
        else ->{
            Picture60ViewHolder(ItemPictureBinding.inflate(inflater, parent, false), listener,flag)
        }
    }
    class Picture60ViewHolder(binding: ItemPictureBinding, val listener: OnItemClickListener?, val flag :Boolean) : SingleViewHolder<ItemPictureBinding, LocalMedia>(binding) {
        override fun setHolder(entity: LocalMedia) {
            Glide.with(view.root)
                .load(entity.path)
                .into(view.imageView)
            var startX = 0f
            var startY = 0f
            var isDragging = false
            if (flag){
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
                                // 这是一个点击事件
                                // 在这里执行点击事件的处理逻辑
                            } else {
                                // 这是一个拖动事件
                                // 在这里执行拖动事件的处理逻辑
                            }
                            isDragging = false
                        }
                    }
                    false
                }
            }else{
                view.imageView.singleClick {
                    listener?.onItemClick(it,layoutPosition)
                }
            }
        }
    }
    class Picture200ViewHolder(binding: ItemPreviewNodePictureBinding, val listener: OnItemClickListener?, val flag :Boolean = true) : SingleViewHolder<ItemPreviewNodePictureBinding, LocalMedia>(binding) {
        override fun setHolder(entity: LocalMedia) {
            Glide.with(view.root)
                .load(entity.path)
                .into(view.imageView)
            var startX = 0f
            var startY = 0f
            var isDragging = false
            if (flag){
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
                                // 这是一个点击事件
                                // 在这里执行点击事件的处理逻辑
                            } else {
                                // 这是一个拖动事件
                                // 在这里执行拖动事件的处理逻辑
                            }
                            isDragging = false
                        }
                    }
                    false
                }
            }else{
                view.imageView.singleClick {
                    listener?.onItemClick(it,layoutPosition)
                }
            }
        }
    }
}
