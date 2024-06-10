package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.member.util.MyType

data class MemberData(
    val group: MemberGroup,
    val mebMember4Part: Member4Part
) : MultipleType {
    override fun viewType(): Int = MyType.MemberData
}
