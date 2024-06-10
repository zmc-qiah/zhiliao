package org.jxxy.debug.home.Fragment.item.bean

import org.jxxy.debug.common.scheme.Scheme
import org.jxxy.debug.corekit.recyclerview.MultipleType

class GridFourBean (
    val title: String? = null,
    val author: String? = null,
    val authorHead:String?=null,
    val content :String?=null,
    val photo:String?=null,
    val scheme: Scheme? = null
): MultipleType {
    override fun viewType(): Int {
        return 10
    }

}