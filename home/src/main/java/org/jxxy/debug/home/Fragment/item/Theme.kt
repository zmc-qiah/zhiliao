package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean

class Theme (type: Int?,name : String?, val topicTitle: String?=null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return THEME
    }
}