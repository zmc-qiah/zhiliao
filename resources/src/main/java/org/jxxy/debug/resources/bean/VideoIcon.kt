package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

data class VideoIcon(
    val myState: MyState,
    val likes: Int,
    val id: Int
) : MultipleType {
    override fun viewType(): Int = Mytype.ITEM_VIDEO_ICON
}
