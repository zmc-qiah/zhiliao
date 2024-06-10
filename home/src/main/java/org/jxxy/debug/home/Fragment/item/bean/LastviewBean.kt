package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType


class LastviewBean (
    val resourceTitle:String?=null,
    val scheme: Scheme?=null
): MultipleType {
    override fun viewType(): Int {
        return 8
    }
}