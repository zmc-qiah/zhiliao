package org.jxxy.debug.home.Fragment.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2
import org.jxxy.debug.corekit.recyclerview.SingleTypeAdapter
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.bean.FallBean
import org.jxxy.debug.home.Fragment.viewholder.FallViewHolder
import org.jxxy.debug.home.databinding.ItemFallComicBinding
import org.jxxy.debug.home.databinding.ItemFallVideoZmcBinding
import org.jxxy.debug.home.databinding.ItemFallZmcBinding

class FallAdapter: SingleTypeAdapter<FallBean>() {
    inner class VideoViewHolder(binding: ItemFallVideoZmcBinding) :
        MultipleViewHolder2<ItemFallVideoZmcBinding,FallBean>(binding) {
        override fun setHolder(entity: FallBean) {
            val fallInfo = entity
            view.videoTv.text = fallInfo?.title
            view.videoTv3.text = fallInfo?.createTime
            view.videoTv2.text = fallInfo?.author
            view.videoTv4.text = fallInfo?.ip
            view.aIv.load(entity.photo,12)
            view.root.singleClick {
                fallInfo?.scheme?.navigation(context)
            }
        }
    }
    inner class ComicViewHolder(binding: ItemFallComicBinding) :
        MultipleViewHolder2<ItemFallComicBinding,FallBean>(binding) {
        override fun setHolder(entity: FallBean) {
            val fallInfo = entity
            view.titleTV.text = fallInfo?.title
            view.timeTV.text = fallInfo?.createTime
            view.ipTV.text = fallInfo?.ip
            view.aIv.load(entity.photo,12)
            view.root.singleClick {
                fallInfo?.scheme?.navigation(context)
            }
        }
    }
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): RecyclerView.ViewHolder?  = when(viewType){
        3 -> VideoViewHolder(ItemFallVideoZmcBinding.inflate(inflater, parent, false))4 -> ComicViewHolder(ItemFallComicBinding.inflate(inflater, parent, false))
        else -> FallViewHolder(ItemFallZmcBinding.inflate(inflater, parent, false))
    }
    override fun getItemViewType(position: Int): Int  = data.get(position).type
}