package org.jxxy.debug.member.bean

import org.jxxy.debug.member.util.MyType

class Note():MyType() {
    val createTime: String? = null
    val noteContent: String? = null
    val resourceId: Long? = null
    val id: Long? = null
    val updateTime: String? = null
    val resourceName: String? = null
    override fun toString(): String {
        return "Note(createTime=$createTime, noteContent=$noteContent, resourceId=$resourceId, updateTime=$updateTime)"
    }
    override fun viewType(): Int = NOTE
}
