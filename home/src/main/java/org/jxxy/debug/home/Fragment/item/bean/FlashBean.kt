package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class FlashBean (
    val title: String? = null,
    val author: String? = null,
    val authorHead: String? = null,
    val photo:String?=null,
    val schemeUrl: Scheme?=null
): MultipleType {
    override fun viewType(): Int {
        return 11
    }

}