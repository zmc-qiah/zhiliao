package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

data class UserDetail(
    val name: String?,
    val field: String?,
    val value: String?,
    val type: Int
) : MultipleType {
    override fun viewType(): Int = type
}
