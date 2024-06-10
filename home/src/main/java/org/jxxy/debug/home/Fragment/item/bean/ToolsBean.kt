package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class ToolsBean(
    val photo: String? = null,
    val name: String? = null,
    val id: Int? = null,
    val scheme: Scheme?=null
): MultipleType {
    override fun viewType(): Int {
        return 15
    }

}