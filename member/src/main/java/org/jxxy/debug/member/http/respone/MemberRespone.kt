package org.jxxy.debug.member.http.respone

import org.jxxy.debug.member.bean.Member4Part
import org.jxxy.debug.member.bean.MemberBrief
import org.jxxy.debug.member.bean.MemberGroup
import org.jxxy.debug.member.bean.MemberScore
import org.jxxy.debug.member.bean.Plan

class MemberRespone(
    val member4Part: Member4Part?,
    val memberBrief: MemberBrief?,
    val memberGroup: MemberGroup?,
    val memberScore: MemberScore?,
    val memberStudyPlan: Plan?,
    val groupForumResponse: GroupForumResponse?
) {
    constructor() : this(null, null, null, null, null, null)

    override fun toString(): String {
        return "MemberRespone(member4Part=$member4Part, memberBrief=$memberBrief, memberGroup=$memberGroup, memberScore=$memberScore, memberStudyPlan=$memberStudyPlan, groupForumResponse=$groupForumResponse)"
    }

}

