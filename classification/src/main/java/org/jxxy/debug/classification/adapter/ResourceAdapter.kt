package com.jxxy.debug.category.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import org.jxxy.debug.classification.databinding.ItemClassificationResourceTextBinding
import org.jxxy.debug.classification.databinding.ItemClassificationResourceVideoBinding
import org.jxxy.debug.classification.databinding.ItemEmptyBinding
import org.jxxy.debug.classification.viewHolder.ClassificationResourceTextViewHolder
import org.jxxy.debug.classification.viewHolder.ClassificationResourcesVideoViewHolder
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.common.databinding.ItemResourceTextBinding
import org.jxxy.debug.common.databinding.ItemResourcesH5Binding
import org.jxxy.debug.common.databinding.ItemResourcesVideoBinding
import org.jxxy.debug.common.viewHolder.ResourceH5ViewHolder
import org.jxxy.debug.common.viewHolder.ResourceTextViewHolder
import org.jxxy.debug.common.viewHolder.ResourcesVideoViewHolder
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder2

class ResourceAdapter : MultipleTypeAdapter() {
    override fun createViewHolder(
        viewType: Int,
        inflater: LayoutInflater,
        parent: ViewGroup
    ): MultipleViewHolder<Resource>? {
//        return when (viewType) {
//            ResourceType.TEXT -> {
//                ResourceTextViewHolder(ItemResourceTextBinding.inflate(inflater, parent, false))
//                    as? MultipleViewHolder<MultipleType>
//            }
//            ResourceType.PICTURE -> {
//                ResourcePictureViewHolder(
//                    ItemResourcePictureBinding.inflate(
//                        inflater,
//                        parent,
//                        false
//                    )
//                )
//                    as? MultipleViewHolder<MultipleType>
//            }
//            ResourceType.VIDEO -> {
//                ResourceVideoViewHolder(ItemResourceVideoBinding.inflate(inflater, parent, false))
//                    as? MultipleViewHolder<MultipleType>
//            }
//            else -> {
//                null
//            }
//        }
        return when(viewType){
            1 -> ClassificationResourceTextViewHolder(ItemClassificationResourceTextBinding.inflate(inflater,parent,false))
            2 -> ResourceH5ViewHolder(ItemResourcesH5Binding.inflate(inflater,parent,false))
            3 -> ClassificationResourcesVideoViewHolder(ItemClassificationResourceVideoBinding.inflate(inflater,parent,false))
            4 -> ClassificationResourceTextViewHolder(ItemClassificationResourceTextBinding.inflate(inflater,parent,false))
            5 -> EmptyItemViewHolder(ItemEmptyBinding.inflate(inflater,parent,false))
            else -> null
        }
    }

    inner class EmptyItemViewHolder(view : ItemEmptyBinding) : MultipleViewHolder2<ItemEmptyBinding,Resource>(view){
        override fun setHolder(entity: Resource) {

        }
    }
}
