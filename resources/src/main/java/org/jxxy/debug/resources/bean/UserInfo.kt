package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

data class UserInfo(
    val userName: String,
    val headPhoto: String
)

data class CommentInfo(
    val tagId: Int,
    val userInfo: UserInfo,
    val comment: String ,
    val commentLikes: Int,
    val commentNum: Int
):Mytype(){
    override fun viewType(): Int = ITEM_Chapter_Comment
    override fun equals(other: Any?): Boolean = if (other is CommentInfo) if (other.comment.equals(this.comment)&&other.userInfo.userName.equals(this.userInfo.userName) ) true else false else false
}
