package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.home.Fragment.http.response.CommonBean

class Notice (type: Int?,name : String?, val advertise: String? = null, val scheme1: Scheme? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return NOTICE
    }
}
