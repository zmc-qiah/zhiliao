package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean

class Share (type: Int?,name : String?, val content: String? = null, val photoUrl: String? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return SHARE
    }
}