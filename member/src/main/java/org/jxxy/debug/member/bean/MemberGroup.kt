package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.member.util.MyType

class MemberGroup(
    val groupId: Long,

    val groupName: String,
    val groupRankList: List<GroupRank>?,
    val peopleNums: Long
) : MultipleType {
    override fun viewType(): Int = MyType.memberGroup
    constructor() : this(0, "", null, 0)
}
