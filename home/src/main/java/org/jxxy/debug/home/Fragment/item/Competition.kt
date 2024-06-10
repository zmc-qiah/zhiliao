package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.CompetitionBean

class Competition (type: Int?,name : String?, val activityInfos: List<CompetitionBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return COMPETITION
    }
}