package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.http.response.RecommendType

class Recommend(type: Int?,name : String?, val lastList: List<RecommendType>? = null) : CommonBean(type,name) {
    override fun viewType(): Int {
        return 1
    }
}