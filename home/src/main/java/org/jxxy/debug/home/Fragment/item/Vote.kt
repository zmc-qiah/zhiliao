package org.jxxy.debug.home.Fragment.item

import org.jxxy.debug.home.Fragment.http.response.CommonBean
import org.jxxy.debug.home.Fragment.item.bean.VoteBean


class Vote(type: Int?,name : String?, val voteInfos: List<VoteBean>? = null) :
    CommonBean(type,name) {
    override fun viewType(): Int {
        return VOTE
    }
}


