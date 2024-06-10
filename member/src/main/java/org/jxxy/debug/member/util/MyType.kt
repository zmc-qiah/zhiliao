package org.jxxy.debug.member.util

import org.jxxy.debug.corekit.recyclerview.MultipleType

abstract class MyType:MultipleType  {
    companion object{
        val MemberInfo = 0
        val MemberPoint = 1
        val MemberData = 2
        val MemberPlan = 3
        val MemberForum = 4
        val member4Part = 5
        val memberGroup = 6
        val Forum = 7
        const val NOTE = 8
        const val PREVIEW = 9
    }
}
