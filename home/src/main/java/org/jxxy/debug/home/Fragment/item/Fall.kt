package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.FallBean

class Fall (type: Int?,name : String?, val fallInfos: List<FallBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return FALL
    }
}
