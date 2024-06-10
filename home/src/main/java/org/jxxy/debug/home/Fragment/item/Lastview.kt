package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.LastviewBean

class Lastview(type: Int?,name : String?, val lastBrowseInfo: LastviewBean? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return LAST
    }
}
