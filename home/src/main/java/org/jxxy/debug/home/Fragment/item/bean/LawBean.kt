package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.corekit.recyclerview.MultipleType

class LawBean(
    val title: String? = null,
    val author: String? = null,
    val authorHead: String? = null,
    val content:String?=null
): MultipleType {
    override fun viewType(): Int {
        return 9
    }
}