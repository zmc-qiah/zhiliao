package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.DiscoveryBean

class Discovery (type: Int?,name : String?, val videoInfos: List<DiscoveryBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return DISCOVERY
    }
}