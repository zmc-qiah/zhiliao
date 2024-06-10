package org.jxxy.debug.resources.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType
import org.jxxy.debug.resources.util.Mytype

class Comment : MultipleType {
    var groupId: String? = null
    var userName: String? = null
    var headPhoto: String? = null
    var comment: String? = null
    var commentPhoto: String? = null
    var commentLikes: Int = -1
    var createTime: String? = null

    override fun toString(): String {
        return "Comment(groupId=$groupId, userName=$userName, comment=$comment, " +
            "commentPhoto=$commentPhoto, commentLikes=$commentLikes, createTime=$createTime)"
    }

    override fun viewType(): Int = Mytype.RESOURCE_COMMTNT
}
