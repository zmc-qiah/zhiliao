package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.FlashBean

class Flash (type: Int?,name : String?, val flashInfos: List<FlashBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return FLASH
    }
}
