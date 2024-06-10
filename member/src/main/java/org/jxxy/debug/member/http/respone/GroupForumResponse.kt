package org.jxxy.debug.member.http.respone

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.member.bean.Forum
import org.jxxy.debug.member.util.MyType

class GroupForumResponse() : MultipleType {
    override fun viewType(): Int = MyType.MemberForum
    val list: ArrayList<Forum> = ArrayList()
    override fun toString(): String {
        return "GroupForumResponse(list=$list)"
    }
}
