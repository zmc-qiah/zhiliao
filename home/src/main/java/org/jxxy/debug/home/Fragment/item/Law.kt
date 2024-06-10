package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.LawBean

class Law (type: Int?,name : String?, val lawInfo: LawBean? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return LAW
    }
}
