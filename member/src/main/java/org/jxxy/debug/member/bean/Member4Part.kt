package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.member.util.MyType

class Member4Part(
    val collectionsNum: Long = 0,
    val followersNum: Long = 0,
    val historiesNum: Long = 0,
    val notesNum: Long = 0
) : MultipleType {
    constructor() : this(0, 0, 0, 0)

    override fun viewType(): Int = MyType.member4Part
}
