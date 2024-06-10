package org.jxxy.debug.common.viewHolder

import navigation
import org.jxxy.debug.common.bean.Resource
import org.jxxy.debug.common.databinding.ItemResourcesH5Binding
import org.jxxy.debug.common.service.goResource
import org.jxxy.debug.corekit.recyclerview.MultipleTypeAdapter
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick

class ResourceH5ViewHolder(val view : ItemResourcesH5Binding) : MultipleViewHolder<Resource>(view.root) {
    override fun setHolder(entity: Resource) {
        view.h5Iv.load(entity.resourcePhoto)
        view.h5Tv.text = entity.resourceTitle
        if(entity.scheme == null){
            view.root.singleClick {
                entity.resourceId?.let {
                    goResource(context,it.toInt())
                }
            }
        }else{
            view.root.singleClick {
                entity.scheme.navigation(context)
            }
        }
    }

}