package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.UserInfo

class Record (type: Int?,name : String?,val userInfo: UserInfo,val num1: Int?=null, val num2: Int?=null, val num3: Int?=null, val num4: Int?=null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return RECORD
    }
}