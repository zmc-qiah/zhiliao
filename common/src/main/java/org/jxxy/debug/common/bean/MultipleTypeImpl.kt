package org.jxxy.debug.common.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class MultipleTypeImpl(val type: Int) : MultipleType {
    override fun viewType(): Int = type
}
