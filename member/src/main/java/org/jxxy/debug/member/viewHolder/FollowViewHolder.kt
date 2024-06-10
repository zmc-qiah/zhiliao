package org.jxxy.debug.member.viewHolder

import org.jxxy.debug.common.util.loads
import org.jxxy.debug.corekit.recyclerview.SingleViewHolder
import org.jxxy.debug.member.bean.Follow
import org.jxxy.debug.member.databinding.ItemFollowItemBinding

class FollowViewHolder(view: ItemFollowItemBinding) : SingleViewHolder<ItemFollowItemBinding, Follow>(view) {
    override fun setHolder(entity: Follow) {
        view.followPhoto.loads(entity.photo, true,context)
        view.followName.text = entity.name ?: "null"
        view.followDescribe.text = entity.describe
    }
}
