package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.HistoryBean

class History (type: Int?,name : String?, val aiHistory: List<HistoryBean>? = null, val scheme: Scheme?=null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return History
    }
}
