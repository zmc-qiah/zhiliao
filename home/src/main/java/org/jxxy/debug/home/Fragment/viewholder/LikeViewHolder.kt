package org.jxxy.debug.home.Fragment.viewholder

import navigation
import org.jxxy.debug.corekit.recyclerview.MultipleViewHolder
import org.jxxy.debug.corekit.util.load
import org.jxxy.debug.corekit.util.singleClick
import org.jxxy.debug.home.Fragment.item.Like
import org.jxxy.debug.home.databinding.ComponentLikeBinding

class LikeViewHolder (val binding: ComponentLikeBinding) :
    MultipleViewHolder<Like>(binding.root) {
    override fun setHolder(entity: Like) {
        binding.like.text = entity.typeName
            binding.imLike1.load(entity.like?.get(0)?.photo,20)
            binding.imLike2.text = entity.like?.get(0)?.label
            binding.imLike3.text = entity.like?.get(0)?.title
            binding.imLike6.text = entity.like?.get(0)?.describe
            binding.imLike1.singleClick {
                entity.like?.get(0)?.schemeDetail?.navigation(context)
            }
        }
    }