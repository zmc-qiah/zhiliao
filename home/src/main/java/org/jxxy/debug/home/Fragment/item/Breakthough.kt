package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.BreakthoughBean

class Breakthough  (type: Int?,name : String?, val thoughInfos: List<BreakthoughBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return BREAKTHOUGH
    }
}
