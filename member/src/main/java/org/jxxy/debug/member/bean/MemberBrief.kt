package org.jxxy.debug.member.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.member.util.MyType

class MemberBrief(
    val achievement: String? = null,
    val headPhoto: String? = null,
    val togetherDay: String? = null,
    val userInfo: String? = null,
    val userLikes: String? = null,
    val userName: String? = null,
    val userPhone: String? = null
) : MultipleType {
    constructor() : this("", "", "", "", "", "", "")

    override fun viewType(): Int = MyType.MemberInfo
    override fun toString(): String {
        return "MemberBrief(achievement=$achievement, headPhoto=$headPhoto, togetherDay=$togetherDay, userInfo=$userInfo, userLikes=$userLikes, userName=$userName, userPhone=$userPhone)"
    }
}
