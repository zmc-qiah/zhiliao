package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

data class ResourceId(var id: Int = -1) : MultipleType {
    override fun viewType(): Int = Mytype.RESOURCE_ID
}
