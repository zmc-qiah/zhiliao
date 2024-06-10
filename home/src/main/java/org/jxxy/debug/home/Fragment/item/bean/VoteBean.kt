package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class VoteBean(
    val point:String?=null,
    val positiveSide:Int?=null,
    val negativeSide:Int ?=null
):MultipleType {
    override fun viewType(): Int {
        return 4
    }
}