package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class HistoryBean(
    val pointInTime: String?=null,
    var historyEvent:String?=null,
    val historyPhoto:String?=null
): MultipleType {
    override fun viewType(): Int {
        return 5
    }

}