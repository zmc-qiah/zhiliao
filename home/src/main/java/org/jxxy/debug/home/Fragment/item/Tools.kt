package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.ToolsBean

class Tools (type: Int?,name : String?, val aiHouses: List<ToolsBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return TOOLS
    }
}
