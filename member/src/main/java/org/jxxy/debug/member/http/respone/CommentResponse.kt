package org.jxxy.debug.member.http.respone

import org.jxxy.debug.member.bean.Forum
import org.jxxy.debug.member.bean.MyPage

class CommentResponse {
    val comment: ArrayList<Forum> = ArrayList()
    val myPage: MyPage = MyPage()
}
