package org.jxxy.debug.resources.http.response

import org.jxxy.debug.resources.bean.Comment
import org.jxxy.debug.resources.bean.MyPage

class CommentResponse {
    val comment: ArrayList<Comment> = ArrayList()
    val myPage: MyPage = MyPage()
}
