package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.TechnologyBean

class Technology (type: Int?,name : String?, val technologyInfos: List<TechnologyBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return TECHNOLOGY
    }
}
