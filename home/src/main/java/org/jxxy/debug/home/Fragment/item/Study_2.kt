package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.home.Fragment.http.response.CommonBean

class Study_2 (type: Int?,name : String?, val scheme: Scheme? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return LEARN_2
    }
}