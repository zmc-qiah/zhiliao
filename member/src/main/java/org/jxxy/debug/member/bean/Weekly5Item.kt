package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

data class Weekly5Item(val type: Int, val name: String? = null, val date: String ? = null) : MultipleType {
    override fun viewType(): Int = type
}
