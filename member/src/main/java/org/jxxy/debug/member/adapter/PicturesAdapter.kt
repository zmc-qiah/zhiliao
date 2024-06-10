package org.jxxy.debug.member.adapter

import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.luck.picture.lib.entity.LocalMedia
import org.jxxy.debug.common.databinding.ItemPictureBinding
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.corekit.util.toast
import org.jxxy.debug.member.myListener.OnItemClickListener

class PicturesAdapter() : SingleTypeAdapter<LocalMedia>() {
    var listener: OnItemClickListener? = null
    val list: List<LocalMedia>
        get() { return data }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder? = PictureViewHolder(ItemPictureBinding.inflate(inflater, parent, false), listener)
    class PictureViewHolder(binding: ItemPictureBinding, val listener: OnItemClickListener?) : SingleViewHolder<ItemPictureBinding, LocalMedia>(binding) {
        override fun setHolder(entity: LocalMedia) {
            Glide.with(view.root)
                .load(entity.path)
                .into(view.imageView)
            var startX = 0f
            var startY = 0f
            var isDragging = false
            view.imageView.singleClick {
                listener?.onItemClick(it,absoluteAdapterPosition)
            }
        }
    }
}
