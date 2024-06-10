package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.ComeBean

class Come (type: Int?,name : String?, val joinInfo: List<ComeBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return COME
    }
}