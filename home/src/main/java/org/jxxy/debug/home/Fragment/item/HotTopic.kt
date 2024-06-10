package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.HotTopicBean

class HotTopic (type: Int?,name : String?, val hotTopic: List<HotTopicBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return HOTTOPIC
    }
}
