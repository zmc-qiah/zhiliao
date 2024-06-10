package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.TedBean

class Ted (type: Int?,name : String?, val tedInfos: List<TedBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return TED
    }
}