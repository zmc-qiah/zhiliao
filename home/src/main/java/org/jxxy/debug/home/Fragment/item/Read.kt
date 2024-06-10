package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.ReadBean

class Read (type: Int?,name : String?, val studyInfos: List<ReadBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return READ
    }
}