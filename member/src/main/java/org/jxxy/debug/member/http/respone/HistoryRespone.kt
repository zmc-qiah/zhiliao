package org.jxxy.debug.member.http.respone

import org.jxxy.debug.member.bean.MyPage
import org.jxxy.debug.member.bean.PastRecords

class HistoryRespone {
    val list: ArrayList<PastRecords> = ArrayList()
    val myPage: MyPage ? = null
    val collectionNums: Long = 0
    val userPhone: String ? = null
    override fun toString(): String {
        return "historyRespone(list=$list, myPage=$myPage, noteNums=$collectionNums, userPhone=$userPhone)"
    }
}
