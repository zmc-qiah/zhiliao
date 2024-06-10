package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.AdvertiseBean

class Advertise (type: Int?,name : String?, val activityInfos: List<AdvertiseBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return ADVERTISE
    }
}