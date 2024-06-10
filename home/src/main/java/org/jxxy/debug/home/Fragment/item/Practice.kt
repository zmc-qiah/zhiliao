package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.PopularizeBean

class Practice (type: Int?,name : String?, val quickEntryInfos: List<PopularizeBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return PRACTICE
    }
}