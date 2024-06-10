package org.jxxy.debug.member.http.respone

import org.jxxy.debug.member.bean.Follow

class FollowRespone(
    val userPhone: String?,
    val collectionNums: String?,
    val list: ArrayList<Follow>?
) {
    constructor() : this(null, null, null)
    override fun toString(): String {
        return "FollowRespone(userPhone=$userPhone, collectionNums=$collectionNums, list=$list)"
    }
}
