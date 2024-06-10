package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.PostureBean


class Posture (type: Int?,name : String?, val charmInfoList: List<PostureBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return POSTURE
    }
}