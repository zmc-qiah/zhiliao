package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean

class Daily (type: Int?,name : String?, val knowledge: String? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return DAILY
    }
}