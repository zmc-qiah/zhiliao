package org.jxxy.debug.member.http.respone

import org.jxxy.debug.common.bean.Resource

class MarkRespone {
    val list: ArrayList<Resource> = ArrayList()
    val collectionNums: Long = 0
    val userPhone: String ? = null
    override fun toString(): String {
        return "MarkRespone(list=$list, collectionNums=$collectionNums, userPhone=$userPhone)"
    }
}
