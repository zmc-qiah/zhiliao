package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.LikeBean

class Like(type: Int?,name : String?, val like: List<LikeBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return LIKE
    }
}
