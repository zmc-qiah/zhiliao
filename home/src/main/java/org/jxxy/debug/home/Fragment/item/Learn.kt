package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.LearnBean

class Learn (type: Int?,name : String?, val totalPicture:String?=null, val totalTitle:String?=null, val hot:String?=null, val studyInfos: List<LearnBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return LEARN
    }
}
