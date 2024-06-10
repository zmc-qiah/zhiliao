package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.member.util.MyType

data class MemberPoint(
    val memberScore: MemberScore ? = null,
    val togetherDay: String ? = "1"
) : MultipleType {
    override fun viewType(): Int = MyType.MemberPoint
}
