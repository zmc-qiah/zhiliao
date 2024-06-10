package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

data class TextBrief(
    val myState: MyState,
    val resourceInfo: ResourceInfo
) : MultipleType {
    override fun viewType(): Int = Mytype.RESOURCE_TEXT_INFO
}
