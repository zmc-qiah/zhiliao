package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.ExpertBean

class Expert(type: Int?,name : String?, val experts: List<ExpertBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return EXPERT
    }
}
